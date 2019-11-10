package com.hillel.evo.adviser.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WebSocketController {

    @MessageMapping("/socket/message")
    @SendTo("/list/business")
    public List<String> getBusiness(String message) {
        List<String> result = new ArrayList<>();
        result.add(message + "1");
        result.add(message + "2");
        return result;
    }
}
