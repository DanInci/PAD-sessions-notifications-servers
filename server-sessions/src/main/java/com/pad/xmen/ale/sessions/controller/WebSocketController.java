package com.pad.xmen.ale.sessions.controller;

import com.pad.xmen.ale.sessions.Application;
import com.pad.xmen.ale.sessions.models.Action;
import com.pad.xmen.ale.sessions.models.ActionKey;
import com.pad.xmen.ale.sessions.models.Event;
import com.pad.xmen.ale.sessions.models.EventKey;
import com.pad.xmen.ale.sessions.security.AuthCtx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
@Controller
public class WebSocketController {

    @Autowired
    private SocketHandler eventSocket;

    @MessageMapping("/room")
    public void processAction(SimpMessageHeaderAccessor sha, Action action) {
        AuthCtx authCtx = (AuthCtx) sha.getUser();
        Application.log.info("Received action from '" + authCtx.getName() +"': " + action.toString());

        switch (action.getKey()) {
            case START:
                if(authCtx.getOwner()) {
                    Event event = new Event(EventKey.START, null);
                    UUID roomId = authCtx.getRoomId();
                    eventSocket.sendEvent(roomId, event);
                    Application.log.info("Sent event to room '" + roomId + "': " + event.toString());
                }
                break;
            case CLICK: {
                Event event = new Event(EventKey.ADD, action.getParameters() + " 10");
                UUID roomId = authCtx.getRoomId();
                eventSocket.sendEvent(roomId, event);
                Application.log.info("Sent event to room '" + roomId + "': " + event.toString());
                break;
            }
        }

    }
}
