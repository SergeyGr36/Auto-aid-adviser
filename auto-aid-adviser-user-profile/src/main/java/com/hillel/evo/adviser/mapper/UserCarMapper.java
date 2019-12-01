package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.repository.SimpleUserRepository;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SimpleUserRepository.class,
        CarIdentificationMapper.class})
public interface UserCarMapper {
//todo прописать маппер з використанням абстрактного маппера
}
