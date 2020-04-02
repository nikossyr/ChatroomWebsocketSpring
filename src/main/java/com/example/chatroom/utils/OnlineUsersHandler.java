package com.example.chatroom.utils;

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
import java.util.stream.Collectors;

@Component
public class OnlineUsersHandler extends TextWebSocketHandler { // for text messages BinaryWebSocketHandler for files

    //https://www.geeksforgeeks.org/copyonwritearraylist-in-java/
    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
//    ConcurrentHashMap<WebSocketSession, String> sessionMap = new ConcurrentHashMap<>();
//    Integer count = 0;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String username = session.getPrincipal().getName();
//        TextMessage outgoingMessage = new TextMessage(userEnteredNickname + ":" + message.toString());
//        TextMessage mm = new TextMessage("hello");
        for (WebSocketSession s : sessions) { // broadcast to all
            String currentReceiver = (String) s.getAttributes().get("NICKNAME");
            try {
                String jsonResponse = new Gson().toJson(sessions.stream()
                        .map(x -> {
                            if (s.getPrincipal().getName().equals(x.getPrincipal().getName())) {
                                return x.getPrincipal().getName() + " (You)";
                            } else {
                                return x.getPrincipal().getName();
                            }
                        })
                        .collect(Collectors.toList()), List.class);
//                    System.out.println(json);
//                    s.sendMessage(new TextMessage(json));

                s.sendMessage(new TextMessage(jsonResponse));

//                s.sendMessage(new TextMessage("HARD_RESET"));
//                for (WebSocketSession ses : sessions) {
//                    String currentNickname = (String) ses.getAttributes().get("NICKNAME");
//                    System.out.println("User Invoked Call: " + userEnteredNickname + ", " + "Current Receiver: " + currentReceiver  + ", " + "Current User: " + currentNickname);
//                    if (currentNickname.equals(currentReceiver)) {
//                        s.sendMessage(new TextMessage("You"));
//                    } else {
//                        s.sendMessage(new TextMessage(currentNickname));
//                    }
//                }
//                s.sendMessage(outgoingMessage);
//                System.out.println(outgoingMessage.toString());
                // s.sendMessage(mm);
            } catch (IOException ex) {
                Logger.getLogger(OnlineUsersHandler.class.getName()).log(Level.SEVERE, null, ex);
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
        handleTextMessage(session, new TextMessage("CONNECTED"));
//
//        if (!sessionMap.containsKey(session)) {
//            sessionMap.put(session, count.toString());
//            count++;
//        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        handleTextMessage(session, new TextMessage("DISCONNECTED"));
//        sessionMap.remove(session);
    }

}

