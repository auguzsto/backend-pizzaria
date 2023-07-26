package io.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.backend.DTO.EventDTO;

@RestController
public class WebSocketController {

    @Autowired
    SimpMessagingTemplate template;

    @PostMapping("/event")
    public ResponseEntity<Void> enableEvent(EventDTO eventDTO) {
        template.convertAndSend("/topic/message", eventDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SendTo("/topic/message")
    public EventDTO broadcastMessage(@Payload EventDTO eventDTO) {
        return eventDTO;
    }
    
}
