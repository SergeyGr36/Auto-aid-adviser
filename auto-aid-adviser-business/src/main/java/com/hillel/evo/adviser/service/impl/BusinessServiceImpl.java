package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.*;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.BusinessUser;
import com.hillel.evo.adviser.entity.Contact;
import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.entity.Location;
import com.hillel.evo.adviser.entity.ServiceForBusiness;
import com.hillel.evo.adviser.entity.WorkTime;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private transient final BusinessMapper mapper;
    private transient final ServiceForBusinessMapper serviceMapper;
    private transient final BusinessRepository businessRepository;
    private transient final BusinessUserRepository userRepository;
    private transient final ServiceForBusinessRepository serviceForBusinessRepository;
    private transient final ImageService imageService;
    private transient final ImageMapper imageMapper;
    private transient final QueryGeneratorService queryGeneratorService;
    private transient final CustomSearch<BusinessFullDto> search;

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
        return serviceMapper.toDtoList(serviceForBusinessRepository.findServicesByBusinessIdAndBusinessUserId(businessId, userId));
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

    public List<BusinessFullDto> findBusinessByServiceAndLocation(String serviceForBusiness,
                                                              double longitude,
                                                              double latitude) {
        var clazz = Business.class;
        var bdto = new SearchTextDTO(clazz, "serviceForBusinesses.name", serviceForBusiness);
        var businessQuery = queryGeneratorService.getTextQuery(bdto);
        var ldto = new SearchSpatialLocationDTO(clazz, "location", 5, latitude, longitude);
        var locationQuery = queryGeneratorService.getSpatialQuery(ldto);
        var dto = new SearchCustomDTO(clazz, new ArrayList<>());
        dto.getQueries().add(businessQuery);
        dto.getQueries().add(locationQuery);
        return search.search(mapper, dto);
    }

    private Set<ServiceForBusiness> getAllServices() {
        return serviceForBusinessRepository.getFetchAll();
    }

    private Set<WorkTime> getTemplateWorkTime() {
        Set<WorkTime> set = new HashSet<>();
        Arrays.asList(DayOfWeek.values()).forEach(
                dayOfWeek -> set.add(new WorkTime(dayOfWeek, LocalTime.MIN, LocalTime.MAX))
        );
        return set;
    }
}