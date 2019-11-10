package com.hillel.evo.adviser.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityUserDetails extends UserDetails {
    Long getUserId();
}
