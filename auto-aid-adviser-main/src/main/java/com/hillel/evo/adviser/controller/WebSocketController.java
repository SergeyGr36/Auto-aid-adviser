package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.WSInputDTO;
import com.hillel.evo.adviser.dto.WSOutputDTO;
import com.hillel.evo.adviser.service.WebSocketService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WebSocketController {

    private transient WebSocketService webSocketService;

    @MessageMapping("/socket/message")
    @SendTo("/list/result")
    public WSOutputDTO getBusiness(WSInputDTO message) {



        //return webSocketService.find(message);

        List<String> result = new ArrayList<>();
        result.add(message.getSearchType());
        result.add(message.getContent());

        WSOutputDTO wsOutputDTO = new WSOutputDTO();

        wsOutputDTO.setResult(result);
        return wsOutputDTO;
    }
}
