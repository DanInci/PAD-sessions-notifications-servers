package com.pad.xmen.ale.controllers;

import com.pad.xmen.ale.Application;
import com.pad.xmen.ale.models.Event;
import com.pad.xmen.ale.models.EventKey;
import com.pad.xmen.ale.models.Notification;
import com.pad.xmen.ale.persistence.*;
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

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @MessageMapping("/events/{roomId}")
    @SendTo("/notifications/{roomId}")
    public Notification processEvent(@DestinationVariable("roomId") UUID roomId, Event event) {
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        Application.log.info("Received event: " + event.toString());

        switch (event.getKey()) {
            case CREATE:
                roomCreated(roomId, event, now);
                break;
            case JOIN:
                playerJoined(roomId, event, now);
                break;
            case LEAVE:
                playerLeft(roomId, event, now);
                break;
            case START:
               gameStarted(roomId, event, now);
                break;
            case ADD:
                addScore(roomId, event, now);
                break;
            case FINISH:
                gameFinished(roomId, event, now);
                break;
        }

        RoomDAO room = roomRepository.findById(roomId).get();

        HistoryDAO history = new HistoryDAO(UUID.randomUUID(), room, event.getKey(), event.getParameters(), now);
        historyRepository.save(history);

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
                playerRepository.save(playerDAO);
            }
        }
    }

    private void gameStarted(UUID roomId, Event event, LocalDateTime now) {
        RoomDAO room = roomRepository.findById(roomId).get();
        room.setStartedAt(now);
        roomRepository.save(room);
    }

    private void playerLeft(UUID roomId, Event event, LocalDateTime now) {
        RoomDAO room = roomRepository.findById(roomId).get();
        List<PlayerDAO> players = room.getPlayers();
        for(PlayerDAO player : players) {
            if(player.getName().equals(event.getParameters())) {
                playerRepository.delete(player);
                players.remove(player);
                roomRepository.save(room);
            }
        }
    }

    private void playerJoined(UUID roomId, Event event, LocalDateTime now) {
        RoomDAO room = roomRepository.findById(roomId).get();

        PlayerDAO player = new PlayerDAO(UUID.randomUUID(), room, event.getParameters(), 0, false);
        playerRepository.save(player);
    }

    private void roomCreated(UUID roomId, Event event, LocalDateTime now) {
        RoomDAO room = new RoomDAO(roomId, now);
        roomRepository.save(room);

        PlayerDAO player = new PlayerDAO(UUID.randomUUID(), room, event.getParameters(), 0, true);
        playerRepository.save(player);
    }
}
