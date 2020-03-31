package com.example.chatroom.config;

import com.example.chatroom.utils.QuestionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new QuestionHandler(), "/messaging");// for mapping a WebSocket handler to a specific URL
        // .addInterceptors(new HttpSessionHandshakeInterceptor()); //https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/socket/server/support/HttpSessionHandshakeInterceptor.html
    }
}
