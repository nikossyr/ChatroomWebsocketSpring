package com.example.chatroom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.Session;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<Session> {

    //    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(new QuestionHandler(), "/chatroom/messaging")// for mapping a WebSocket handler to a specific URL
//                .addHandler(new OnlineUsersHandler(), "/chatroom/online")
//                .addInterceptors(new HttpSessionHandshakeInterceptor());//https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/socket/server/support/HttpSessionHandshakeInterceptor.html
////                .addInterceptors(new SessionConnectEvent());
//    }
    @Autowired
    private OnlineStatusMessageInterceptor onlineStatusMessageInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic/", "/user", "/queue");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").withSockJS();
//                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(onlineStatusMessageInterceptor);
    }
}
