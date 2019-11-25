package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.BusinessUser;
import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.exception.CreateResourceException;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.mapper.BusinessMapper;
import com.hillel.evo.adviser.mapper.ImageMapper;
import com.hillel.evo.adviser.mapper.ServiceForBusinessMapper;
import com.hillel.evo.adviser.repository.BusinessRepository;
import com.hillel.evo.adviser.repository.BusinessUserRepository;
import com.hillel.evo.adviser.repository.ServiceForBusinessRepository;
import com.hillel.evo.adviser.service.BusinessService;
import com.hillel.evo.adviser.service.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusinessServiceImpl implements BusinessService {

    private transient final BusinessMapper mapper;
    private transient final ServiceForBusinessMapper serviceMapper;
    private transient final BusinessRepository businessRepository;
    private transient final BusinessUserRepository userRepository;
    private transient final ServiceForBusinessRepository serviceForBusinessRepository;
    private transient final ImageService imageService;
    private transient final ImageMapper imageMapper;

    //private static final Logger LOGGER = LoggerFactory.getLogger(BusinessServiceImpl.class);

    @Autowired
    public BusinessServiceImpl(BusinessMapper mapper, ServiceForBusinessMapper serviceMapper, BusinessRepository businessRepository, BusinessUserRepository userRepository, ServiceForBusinessRepository serviceForBusinessRepository, ImageService imageService, ImageMapper imageMapper) {
        this.mapper = mapper;
        this.serviceMapper = serviceMapper;
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
        this.serviceForBusinessRepository = serviceForBusinessRepository;
        this.imageService = imageService;
        this.imageMapper = imageMapper;
    }

    @Override
    public BusinessDto createBusiness(final BusinessDto dto, Long userId) {
        return createBusiness(dto, userId, Optional.empty());
    }

    @Override
    @Transactional
    public BusinessDto createBusiness(final BusinessDto dto, Long userId, Optional<MultipartFile> file) {
        BusinessUser user = userRepository.getOne(userId);
        Business business = businessRepository.save(mapper.toEntity(dto, user));
        Optional<Image> image = file.flatMap((f) -> imageService.create(userId, business.getId(), f));
        image.ifPresent(business.getImages()::add);
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
    public ImageDto addImage(Long userId, Long businessId, MultipartFile file) {
        Business business = businessRepository.findByIdAndBusinessUserId(businessId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found"));
        Image image = imageService.create(userId, businessId, file)
                .orElseThrow(() -> new CreateResourceException("Image not saved"));
        business.getImages().add(image);
        businessRepository.save(business);
        return imageMapper.toDto(image);
    }

    @Override
    public boolean deleteImage(Long userId, Long businessId, ImageDto dto) {
        Image image = businessRepository.findImageByBusinessUserIdAndBusinessIdAndImageId(userId, businessId, dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
        return imageService.delete(image);
    }
}