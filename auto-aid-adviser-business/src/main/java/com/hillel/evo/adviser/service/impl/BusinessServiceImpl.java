package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.BusinessFullDto;
import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.entity.*;
import com.hillel.evo.adviser.exception.CreateResourceException;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.mapper.BusinessMapper;
import com.hillel.evo.adviser.mapper.ImageMapper;
import com.hillel.evo.adviser.mapper.ServiceForBusinessMapper;
import com.hillel.evo.adviser.repository.BusinessRepository;
import com.hillel.evo.adviser.repository.BusinessUserRepository;
import com.hillel.evo.adviser.repository.ServiceForBusinessRepository;
import com.hillel.evo.adviser.search.CustomSearch;
import com.hillel.evo.adviser.service.BusinessService;
import com.hillel.evo.adviser.service.QueryGeneratorService;
import com.hillel.evo.adviser.service.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

@Service
public class BusinessServiceImpl implements BusinessService {

    private transient final BusinessMapper mapper;
    private transient final ServiceForBusinessMapper serviceMapper;
    private transient final BusinessRepository businessRepository;
    private transient final BusinessUserRepository userRepository;
    private transient final ServiceForBusinessRepository serviceForBusinessRepository;
    private transient final ImageService imageService;
    private transient final ImageMapper imageMapper;
    private transient final QueryGeneratorService queryGeneratorService;
    private transient final CustomSearch<Business>search;

    //private static final Logger LOGGER = LoggerFactory.getLogger(BusinessServiceImpl.class);

    @Autowired
    public BusinessServiceImpl(BusinessMapper mapper, ServiceForBusinessMapper serviceMapper, BusinessRepository businessRepository, BusinessUserRepository userRepository, ServiceForBusinessRepository serviceForBusinessRepository, ImageService imageService, ImageMapper imageMapper, QueryGeneratorService queryGeneratorService, CustomSearch search) {
        this.mapper = mapper;
        this.serviceMapper = serviceMapper;
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
        this.serviceForBusinessRepository = serviceForBusinessRepository;
        this.imageService = imageService;
        this.imageMapper = imageMapper;
        this.queryGeneratorService = queryGeneratorService;
        this.search = search;
    }

    @Override
    public BusinessDto createBusiness(final BusinessDto dto, Long userId) {
        return createBusiness(dto, userId, new ArrayList<>());
    }

    @Override
    @Transactional
    public BusinessDto createBusiness(BusinessDto dto, Long userId, List<MultipartFile> files) {
        BusinessUser user = userRepository.getOne(userId);
        Business business = businessRepository.save(mapper.toEntity(dto, user));
        List<Image> images = imageService.create(userId, business.getId(), files)
                .orElseThrow(() -> new CreateResourceException("Image not saved"));
        business.getImages().addAll(images);

        return mapper.toDto(business);
    }

    @Override
    public List<BusinessDto> findAllByUser(Long userId) {
        return mapper.listToDto(businessRepository.findBusinessesFetchServicesByBusinessUser_Id(userId));
    }

    @Override
    public BusinessDto findBusinessById(Long id, Long userId) {
        return mapper.toDto(businessRepository.findByIdAndBusinessUserId(id, userId)
                     .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public BusinessDto updateBusiness(final BusinessDto dto, Long userId) {
        return createBusiness(dto, userId);
    }

    @Override
    @Transactional
    public void deleteBusiness(Long id, Long userId) {
        Business business = businessRepository
                .findByIdAndBusinessUserId(id, userId)
                .orElseThrow(ResourceNotFoundException::new);
        business.getImages().forEach(img -> imageService.delete(img));
        businessRepository.delete(business);
    }

    @Override
    public List<ServiceForBusinessDto> findServicesByBusinessId(Long businessId, Long userId) {
        return serviceMapper.toDto(serviceForBusinessRepository.findServicesByBusinessIdAndBusinessUserId(businessId, userId));
    }

    @Override
    public List<ImageDto> findImagesByBusinessId(Long businessId) {
        return imageMapper.toListDto(businessRepository.findImagesByBusinessId(businessId));
    }

    @Override
    @Transactional
    public List<ImageDto> addImages(@NotNull Long userId, @NotNull Long businessId, @NotEmpty List<MultipartFile> files) {
        Business business = businessRepository.findByIdAndBusinessUserId(businessId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found"));
        List<Image> imageList = imageService.create(userId, businessId, files)
                .orElseThrow(() -> new CreateResourceException("Image not saved"));
        business.getImages().addAll(imageList);
        businessRepository.save(business);
        return imageMapper.toListDto(imageList);
    }

    @Override
    public boolean deleteImage(@NotNull Long userId, @NotNull Long businessId, @NotNull Long imageId) {
        Image image = businessRepository.findImageByBusinessUserIdAndBusinessIdAndImageId(userId, businessId, imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
        return imageService.delete(image);
    }

    @Override
    public BusinessFullDto createTemplateBusiness() {
        Business business = new Business();
        business.setLocation(new Location());
        business.setContact(new Contact());
        business.setWorkTimes(getTemplateWorkTime());
        business.setServiceForBusinesses(getAllServices());
        return mapper.toFullDto(business);
    }

    public List<BusinessDto> findByBusinessTypeServiceTypeLocation( String serviceForBusiness, double longtitude, double latitude ) {
        var serviceForBusinessQuery = queryGeneratorService.getTextQuery(Business.class,"serviceForBusinnes.name", serviceForBusiness);
        var locationTypeQuery = queryGeneratorService.getSpatialQuery(Business.class, 12, 12,12);
        var entities = search.search(Business.class, serviceForBusinessQuery, locationTypeQuery);
        List<BusinessDto> result = new ArrayList<>();
        entities.forEach(e -> result.add(mapper.toDto(e)));
        return result;

    }

    private Set<ServiceForBusiness> getAllServices() {
        Set<ServiceForBusiness> set = serviceForBusinessRepository.getFetchAll();
        return set;
    }

    private Set<WorkTime> getTemplateWorkTime() {
        Set<WorkTime> set = new HashSet();
        Arrays.asList(DayOfWeek.values()).forEach(
                dayOfWeek -> set.add(new WorkTime(dayOfWeek, LocalTime.MIN, LocalTime.MAX))
        );
        return set;
    }
}