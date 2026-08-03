package com.securerealty.backend.Test;
import java.lang.reflect.Type;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class StompTestClient {

    public static void main(String[] args) {
    	WebSocketStompClient stompClient =
    	        new WebSocketStompClient(new StandardWebSocketClient());

    	stompClient.setMessageConverter(
    	        new MappingJackson2MessageConverter());
    }

}