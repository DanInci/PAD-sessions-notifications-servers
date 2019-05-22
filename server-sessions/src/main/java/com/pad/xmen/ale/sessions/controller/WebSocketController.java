package com.pad.xmen.ale.sessions.controller;

import com.pad.xmen.ale.sessions.Application;
import com.pad.xmen.ale.sessions.extras.Util;
import com.pad.xmen.ale.sessions.models.Action;
import com.pad.xmen.ale.sessions.models.Event;
import com.pad.xmen.ale.sessions.models.EventKey;
import com.pad.xmen.ale.sessions.security.AuthCtx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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
        Date now = new Date();
        AuthCtx authCtx = (AuthCtx) sha.getUser();
        Application.log.info("Received action from '" + authCtx.getName() +"': " + action.toString());

        switch (action.getKey()) {
            case START:
                if(authCtx.getOwner()) {
                    Event event = new Event(EventKey.START, null);
                    UUID roomId = authCtx.getRoomId();
                    eventSocket.sendEvent(roomId, event);
                    Application.log.info("Sent event for room '" + roomId + "': " + event.toString());

                    Integer gameSeconds = Integer.valueOf(action.getParameters());
                    Date finishTime = new Date(now.getTime() + gameSeconds * 1000);
                    scheduleFinishEvent(roomId, finishTime);
                }
                break;
            case CLICK: {
                Event event = new Event(EventKey.ADD, action.getParameters() + " 10");
                UUID roomId = authCtx.getRoomId();
                eventSocket.sendEvent(roomId, event);
                Application.log.info("Sent event for room '" + roomId + "': " + event.toString());
                break;
            }
        }
    }

    @Async
    public void scheduleFinishEvent(UUID roomId, Date finishTime) {
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        TaskScheduler scheduler = new ConcurrentTaskScheduler(localExecutor);

        scheduler.schedule(finishEventRunnable(roomId), finishTime);
        Application.log.info("Room '" + roomId + "' is scheduled to finish at " + Util.dateFormatter.format(finishTime));
    }

    private Runnable finishEventRunnable(UUID roomId) {
        return () -> {
            Event event = new Event(EventKey.FINISH, null);
            eventSocket.sendEvent(roomId, event);
            Application.log.info("Sent event for room '" + roomId + "': " + event.toString());
        };
    }
}
