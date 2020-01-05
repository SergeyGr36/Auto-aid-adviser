package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.BusinessFullDto;
import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BusinessService {
    BusinessDto createBusiness(BusinessDto dto, Long userId);

    BusinessDto createBusiness(BusinessDto dto, Long userId, List<MultipartFile> files);

    List<BusinessDto> findAllByUser(Long userId);

    BusinessDto findBusinessById(Long id, Long userId);

    BusinessDto updateBusiness(BusinessDto dto, Long userId);

    void deleteBusiness(Long id, Long userId);

    List<ServiceForBusinessDto> findServicesByBusinessId(Long businessId, Long userId);

    List<ImageDto> findImagesByBusinessId(Long businessId);

    //    ImageDto addImage(Long userId, Long businessId, MultipartFile file);
    List<ImageDto> addImages(Long userId, Long businessId, List<MultipartFile> files);

    boolean deleteImage(Long userId, Long businessId, Long imageId);

    BusinessFullDto createTemplateBusiness();

    List<BusinessDto> findBusinessByTypeAndLocation(String serviceForBusiness,
                                                    double longitude,
                                                    double latitude);
}
