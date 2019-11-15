package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.WSInputDTO;
import com.hillel.evo.adviser.dto.WSOutputDTO;
import com.hillel.evo.adviser.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    private transient WebSocketService webSocketService;

    @Autowired
    public void setWebSocketService(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @MessageMapping("/socket/message")
    @SendTo("/list/result")
    public WSOutputDTO getBusiness(WSInputDTO message) {

        return webSocketService.find(message);
    }
}
