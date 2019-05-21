package com.pad.xmen.ale.notifications.controllers;

import com.pad.xmen.ale.notifications.Application;
import com.pad.xmen.ale.notifications.models.Event;
import com.pad.xmen.ale.notifications.models.Notification;
import com.pad.xmen.ale.notifications.persistence.*;
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

        boolean successful = false;
        Notification notification = null;
        switch (event.getKey()) {
            case CREATE:
                successful = roomCreated(roomId, event, now);
                break;
            case JOIN:
                successful = playerJoined(roomId, event, now);
                break;
            case LEAVE:
                successful = playerLeft(roomId, event, now);
                break;
            case START:
                successful = gameStarted(roomId, event, now);
                break;
            case ADD:
                successful = addScore(roomId, event, now);
                break;
            case FINISH:
                successful = gameFinished(roomId, event, now);
                break;
        }

        if(successful) {
            Optional<RoomDAO> maybeRoom = roomRepository.findById(roomId);
            if(maybeRoom.isPresent()) {
                HistoryDAO history = new HistoryDAO(UUID.randomUUID(), maybeRoom.get(), event.getKey(), event.getParameters(), now);
                historyRepository.save(history);

                notification = new Notification(roomId, event.getKey(), event.getParameters(), now);
            };
        }
        else {
            Application.log.warn("Event was rejected");
        }
        return notification;
    }

    private boolean gameFinished(UUID roomId, Event event, LocalDateTime now) {
        Optional<RoomDAO> maybeRoom = roomRepository.findById(roomId);
        if(maybeRoom.isPresent()) {
            RoomDAO room = maybeRoom.get();
            if(isGameStarted(room) && !isGameFinished(room)) {
                room.setFinishedAt(now);
                roomRepository.save(room);
                return true;
            }
        }
        return false;
    }

    private boolean addScore(UUID roomId, Event event, LocalDateTime now) {
        Optional<RoomDAO> maybeRoom = roomRepository.findById(roomId);
        if(maybeRoom.isPresent()) {
            RoomDAO room = maybeRoom.get();
            if(isGameStarted(room) && !isGameFinished(room)) {
                String[] split = event.getParameters().split(" ");
                String name = split[0];
                Integer score = Integer.valueOf(split[1]);
                for(PlayerDAO playerDAO : room.getPlayers()) {
                    if(playerDAO.getName().equals(name)) {
                        playerDAO.setScore(playerDAO.getScore() + score);
                        playerRepository.save(playerDAO);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean gameStarted(UUID roomId, Event event, LocalDateTime now) {
        Optional<RoomDAO> maybeRoom = roomRepository.findById(roomId);
        if(maybeRoom.isPresent()) {
            RoomDAO room = maybeRoom.get();
            if(!isGameStarted(room)) {
                room.setStartedAt(now);
                roomRepository.save(room);
                return true;
            }
        }
        return false;
    }

    private boolean playerLeft(UUID roomId, Event event, LocalDateTime now) {
        Optional<RoomDAO> maybeRoom = roomRepository.findById(roomId);
        if(maybeRoom.isPresent()) {
            RoomDAO room = maybeRoom.get();
            if(!isGameFinished(room)) {
                List<PlayerDAO> players = room.getPlayers();
                for(PlayerDAO player : players) {
                    if(player.getName().equals(event.getParameters())) {
                        playerRepository.delete(player);
                        if(player.getOwner()) {
                            room.setFinishedAt(now);
                            roomRepository.save(room);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean playerJoined(UUID roomId, Event event, LocalDateTime now) {
        Optional<RoomDAO> maybeRoom = roomRepository.findById(roomId);
        if(maybeRoom.isPresent()) {
            RoomDAO room = maybeRoom.get();
            if(!isGameStarted(room)) {
                if(!doesPlayerExist(room.getPlayers(), event.getParameters())) {
                    if(!isGameFinished(room)) {
                        PlayerDAO player = new PlayerDAO(UUID.randomUUID(), room, event.getParameters(), 0, false);
                        playerRepository.save(player);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean roomCreated(UUID roomId, Event event, LocalDateTime now) {
        if(!roomRepository.findById(roomId).isPresent()) {
            RoomDAO room = new RoomDAO(roomId, now);
            roomRepository.save(room);

            PlayerDAO player = new PlayerDAO(UUID.randomUUID(), room, event.getParameters(), 0, true);
            playerRepository.save(player);
            return true;
        }
        return false;
    }

    private boolean isGameFinished(RoomDAO room) {
        return room.getFinishedAt() != null;
    }

    private boolean isGameStarted(RoomDAO room) {
        return room.getStartedAt() != null;
    }

    private boolean doesPlayerExist(List<PlayerDAO> players, String name) {
        for(PlayerDAO player : players) {
            if(player.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
