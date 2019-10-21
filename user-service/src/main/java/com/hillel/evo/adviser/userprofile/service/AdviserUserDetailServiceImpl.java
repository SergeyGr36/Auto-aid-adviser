package com.hillel.evo.adviser.userprofile.service;

import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.userprofile.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.userprofile.message.Message;
import com.hillel.evo.adviser.userprofile.repository.AdviserUserDetailRepository;

public class AdviserUserDetailServiceImpl implements AdviserUserDetailService {
    private final AdviserUserDetailRepository repository;

    public AdviserUserDetailServiceImpl(AdviserUserDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public AdviserUserDetails activation(String code) {
        AdviserUserDetails userDetails = repository.findByActivationCode(code);
        if (userDetails != null) {
            userDetails.setActive(true);
            userDetails.setActivationCode(null);
            return repository.save(userDetails);
        } else {
            throw new ResourceNotFoundException(Message.ACTIVE_CODE_NOT_FOUND.getDiscript());
        }
    }
}
