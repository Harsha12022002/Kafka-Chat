package com.example.springkafka;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class SimpleTextHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        sessions.add(session);
        new Thread(()->{
            try{
                Thread.sleep(100);
                sendCount();

            }catch(Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {
        sessions.remove(session);
    }
    
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        Map<String, Object> response = new HashMap<>();
        response.put("type", "chat");
        response.put("message", message.getPayload());
        String json = new ObjectMapper().writeValueAsString(response);
        for(WebSocketSession ses:sessions){
            if(ses.isOpen()&&ses!=session){
                ses.sendMessage(new TextMessage(json));
            }
        }
    }

    public void sendCount()throws IOException{
        String json = "{\"type\":\"count\",\"count\":" + sessions.size() + "}";
        for(WebSocketSession session:sessions){
            if(session.isOpen()){
                synchronized(session){
                    session.sendMessage(new TextMessage(json));
                }
            }
        }
    }

  public void broadcast(String message) throws IOException {
    Map<String, Object> response = new HashMap<>();
    response.put("type", "chat");
    response.put("message", message);

    String json = new ObjectMapper().writeValueAsString(response);

    for (WebSocketSession session : sessions) {
        if (session.isOpen()) {
            System.out.print(sessions.size());
            session.sendMessage(new TextMessage(json));
        }
    }
}

}
