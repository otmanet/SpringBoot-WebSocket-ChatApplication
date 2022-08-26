package com.example.AppChats.Controller;

import com.example.AppChats.model.MessageChat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@Component
public class WebSocketListener {

    public static final Logger logger= LoggerFactory.getLogger(WebSocketListener.class);
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionDisconnectEvent event){
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            logger.info("User Disconnected : " + username);

            MessageChat messageChat = new MessageChat();
            messageChat.setType(MessageChat.MessageType.LEAVE);
            messageChat.setSender(username);

            messagingTemplate.convertAndSend("/topic/public", messageChat);
        }
    }
}
