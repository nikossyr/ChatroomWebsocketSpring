package com.example.chatroom.resources;

import com.example.chatroom.stompsockettransfer.Message;
import com.example.chatroom.stompsockettransfer.OutputMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

@Controller
public class ChatroomController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    //    Getting users subscribed
    @Autowired
    private SimpUserRegistry userRegistry;

    @MessageMapping("/chat")
    public void sendSpecific(
            @Payload Message msg,
            MessageHeaders messageHeaders,
            Principal principal,
            @Header("simpSessionId") String sessionId) {
        OutputMessage out = new OutputMessage(
                principal.getName(),
                msg.getText(),
                new SimpleDateFormat("HH:mm").format(new Date()));
        if (msg.getTo().equals("groupchat")) {
            ArrayList<String> recipientUsernames = userRegistry.getUsers().stream()
                    .map(SimpUser::getName)
                    .filter(username -> !username.equals(principal.getName()))
                    .collect(Collectors.toCollection(ArrayList::new));
            for (String username : recipientUsernames) {
                simpMessagingTemplate.convertAndSendToUser(username, "/queue/specific-user", out);
            }
        } else {
            simpMessagingTemplate.convertAndSendToUser(msg.getTo(), "/queue/specific-user", out);
        }
    }
}
