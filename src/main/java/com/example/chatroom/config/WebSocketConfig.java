package com.example.chatroom.config;

import com.example.chatroom.utils.OnlineUsersHandler;
import com.example.chatroom.utils.QuestionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new QuestionHandler(), "chatroom/messaging")// for mapping a WebSocket handler to a specific URL
                .addHandler(new OnlineUsersHandler(), "chatroom/online")
                .addInterceptors(new HttpSessionHandshakeInterceptor());//https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/socket/server/support/HttpSessionHandshakeInterceptor.html
//                .addInterceptors(new SessionConnectEvent());
    }


}
