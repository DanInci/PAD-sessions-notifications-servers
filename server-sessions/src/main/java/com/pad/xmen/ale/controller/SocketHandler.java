package com.pad.xmen.ale.controller;

import com.pad.xmen.ale.Application;
import com.pad.xmen.ale.models.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
@Component
public class SocketHandler implements StompSessionHandler {

    @Value("${notifications.server}")
    private String notificationsServerUrl;

    @PostConstruct
    private void connect() {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

        String url = "ws://" + notificationsServerUrl + "/event";
        stompClient.connect(url, this);

        new Scanner(System.in).nextLine(); //Don't close immediately.
    }

    private StompSession session;

    public boolean sendEvent(UUID roomId, Event event) {
        if(session != null) {
            session.send("/events/" + roomId, event);
            return true;
        }
        return false;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        Application.logger.info("New session established : " + session.getSessionId());
        this.session = session;
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        Application.logger.error("Error: " + exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        Application.logger.error("Error: " + exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Event.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Application.logger.info("Received: " + payload);
    }
}
