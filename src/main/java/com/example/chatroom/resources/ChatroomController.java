package com.example.chatroom.resources;

import com.example.chatroom.pojo.WebSocketMessagePOJO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatroomController {

    @MessageMapping("/greetings")
    @SendTo("/topic/greetings")
    public WebSocketMessagePOJO getMessage(@Payload String message, Principal principal) {
        WebSocketMessagePOJO messagePOJO = new WebSocketMessagePOJO(principal.getName(), message);
        return messagePOJO;
    }
}
