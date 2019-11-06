package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.parameter.MessageParameters;

public interface EmailService {
    boolean sendMessage(MessageParameters dto);
}
