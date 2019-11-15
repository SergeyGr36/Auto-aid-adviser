package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.WSInputDTO;
import com.hillel.evo.adviser.dto.WSOutputDTO;

public interface WebSocketService {

    WSOutputDTO find(WSInputDTO dto);
}
