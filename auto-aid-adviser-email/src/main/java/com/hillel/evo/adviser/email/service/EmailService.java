package com.hillel.evo.adviser.email.service;

import com.hillel.evo.adviser.email.dto.MessageDto;

public interface EmailService {
    void sendMessage(MessageDto dto);
}
