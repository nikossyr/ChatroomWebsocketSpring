package com.example.chatroom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

// This class intercepts the stomp subscribe messages and informs other users which user connected and the same user who
// is already online.
@Component
public class OnlineStatusMessageInterceptor implements ChannelInterceptor {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private SimpUserRegistry userRegistry;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            if (accessor.getDestination().equals("/user/queue/specific-user")) {
                simpMessagingTemplate.convertAndSendToUser(
                        accessor.getUser().getName(), "/queue/online",
                        userRegistry.getUsers().stream()
                                .map(SimpUser::getName)
                                .filter(username -> !username.equals(accessor.getUser().getName()))
                                .collect(Collectors
                                        .toCollection(ArrayList::new)));
                simpMessagingTemplate.convertAndSend("/user/queue/online", Arrays.asList(accessor.getUser().getName()));
            }
        }
        if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            simpMessagingTemplate.convertAndSend("/user/queue/offline", accessor.getUser().getName());
        }
        return message;
    }

//    @Override
//    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
//        StompHeaderAccessor accessor =
//                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
//            if (accessor.getDestination().equals("/user/queue/specific-user")) {
//                simpMessagingTemplate.convertAndSendToUser(
//                        accessor.getUser().getName(), "/queue/online",
//                        userRegistry.getUsers().stream().map(SimpUser::getName).collect(Collectors
//                                .toCollection(ArrayList::new)));
//            }
//        }
//        if (StompCommand.UNSUBSCRIBE.equals(accessor.getCommand())) {
//            simpMessagingTemplate.convertAndSend("/queue/offline", accessor.getUser().getName());
//        }
//    }
}
