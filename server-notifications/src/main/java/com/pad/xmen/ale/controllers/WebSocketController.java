package com.pad.xmen.ale.controllers;

import com.pad.xmen.ale.Application;
import com.pad.xmen.ale.models.Event;
import com.pad.xmen.ale.models.EventKey;
import com.pad.xmen.ale.models.Notification;
import com.pad.xmen.ale.persistence.HistoryDAO;
import com.pad.xmen.ale.persistence.PlayerDAO;
import com.pad.xmen.ale.persistence.RoomDAO;
import com.pad.xmen.ale.persistence.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-20
 */
@Controller
public class WebSocketController {

    @Autowired
    private RoomRepository roomRepository;

    @MessageMapping("/events/{roomId}")
    @SendTo("/notifications/{roomId}")
    public Notification processEvent(@DestinationVariable("roomId") UUID roomId, Event event) {
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        Application.log.info("Received event: " + event.toString());

        switch (event.getKey()) {
            case JOIN:
                playerJoined(roomId, event, now);
            case LEAVE:
                playerLeft(roomId, event, now);
            case START:
               gameStarted(roomId, event, now);
            case ADD:
                addScore(roomId, event, now);
            case FINISH:
                gameFinished(roomId, event, now);
        }

        RoomDAO room = roomRepository.findById(roomId).get();
        HistoryDAO history = new HistoryDAO(EventKey.CREATE, event.getParameters(), now);
        room.getHistory().add(history);
        roomRepository.save(room);

        return new Notification(roomId, event.getKey(), event.getParameters(), now);
    }

    private void gameFinished(UUID roomId, Event event, LocalDateTime now) {
        RoomDAO room = roomRepository.findById(roomId).get();
        room.setFinishedAt(now);
        roomRepository.save(room);
    }

    private void addScore(UUID roomId, Event event, LocalDateTime now) {
        RoomDAO room = roomRepository.findById(roomId).get();
        String[] split = event.getParameters().split(" ");
        String name = split[0];
        Integer score = Integer.valueOf(split[1]);
        for(PlayerDAO playerDAO : room.getPlayers()) {
            if(playerDAO.getName().equals(name)) {
                playerDAO.setScore(playerDAO.getScore() + score);
            }
        }
        roomRepository.save(room);
    }

    private void gameStarted(UUID roomId, Event event, LocalDateTime now) {
        RoomDAO room = roomRepository.findById(roomId).get();
        room.setStartedAt(now);
        roomRepository.save(room);
    }

    private void playerLeft(UUID roomId, Event event, LocalDateTime now) {
        RoomDAO room = roomRepository.findById(roomId).get();
        room.getPlayers().removeIf(playerDAO -> playerDAO.getName().equals(event.getParameters()));
        roomRepository.save(room);
    }

    private void playerJoined(UUID roomId, Event event, LocalDateTime now) {
        RoomDAO room = roomRepository.findById(roomId).get();
        PlayerDAO player = new PlayerDAO(event.getParameters(), 0, false);
        room.getPlayers().add(player);

        roomRepository.save(room);
    }

    private void roomCreated(UUID roomId, Event event, LocalDateTime now) {
        RoomDAO room = new RoomDAO(roomId, now);
        room.setHistory(new ArrayList<>());

        PlayerDAO player = new PlayerDAO(event.getParameters(), 0, true);
        room.setPlayers(Collections.singletonList(player));

        roomRepository.save(room);
    }
}
