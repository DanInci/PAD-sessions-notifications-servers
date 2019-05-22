package com.pad.xmen.ale.sessions.controller;

import com.pad.xmen.ale.sessions.Application;
import com.pad.xmen.ale.sessions.models.Event;
import com.pad.xmen.ale.sessions.models.EventKey;
import com.pad.xmen.ale.sessions.security.AuthCtx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-22
 */
@Component
public class SessionDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {

    @Autowired
    private SocketHandler eventSocket;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent disconnectEvent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(disconnectEvent.getMessage());
        AuthCtx authCtx = (AuthCtx) sha.getUser();
        UUID roomId = authCtx.getRoomId();
        String name = authCtx.getName();

        if(authCtx.getOwner()) {
            Application.log.info("Owner '" + name + "' has left the room '" + roomId + "'!");
        }
        else {
            Application.log.info("Player '" + name + "' has left the room '" + roomId + "'!");
        }

        Event event = new Event(EventKey.LEAVE, name);
        eventSocket.sendEvent(roomId, event);
        Application.log.info("Sent event for room '" + roomId + "': " + event.toString());
    }
}
