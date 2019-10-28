package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.parameters.MessageParameters;

public interface EmailService {
    void sendMessage(MessageParameters dto);
}
