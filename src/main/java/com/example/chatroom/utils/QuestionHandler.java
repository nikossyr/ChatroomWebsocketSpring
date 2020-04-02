package com.example.chatroom.utils;

import com.example.chatroom.pojo.WebSocketMessagePOJO;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class QuestionHandler extends TextWebSocketHandler { // for text messages BinaryWebSocketHandler for files

    //https://www.geeksforgeeks.org/copyonwritearraylist-in-java/
    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
//    ConcurrentHashMap<WebSocketSession, String> sessionMap = new ConcurrentHashMap<>();
//    Integer count = 0;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String username = session.getPrincipal().getName();
//        TextMessage mm = new TextMessage("hello");
        for (WebSocketSession s : sessions) { // broadcast to all
//s.getAttributes().put(key, s);
            if (username.equals(s.getPrincipal().getName())) {
                continue;
            }
            try {
                WebSocketMessagePOJO messagePOJO = new WebSocketMessagePOJO(username, message.getPayload());
                String messageJson = new Gson().toJson(messagePOJO, WebSocketMessagePOJO.class);
                s.sendMessage(new TextMessage(messageJson));
                // s.sendMessage(mm);
            } catch (IOException ex) {
                Logger.getLogger(QuestionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

//        for (WebSocketSession s : sessionMap.keySet()) {
//            try {
//                s.sendMessage(new TextMessage(sessionMap.get(session) + ": " + message.getPayload()));
////                // s.sendMessage(mm);
//            } catch (IOException ex) {
//                Logger.getLogger(QuestionHandler.class.getName()).log(Level.SEVERE, null, ex);
////            }
//            }
//        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        if (!sessions.contains(session)) {
            sessions.add(session);// I want to keep all my session in a list
        }
//
//        if (!sessionMap.containsKey(session)) {
//            sessionMap.put(session, count.toString());
//            count++;
//        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
//        sessionMap.remove(session);
    }

}

