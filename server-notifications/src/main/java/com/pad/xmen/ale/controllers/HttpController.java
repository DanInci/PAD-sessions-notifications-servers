package com.pad.xmen.ale.controllers;

import com.pad.xmen.ale.errors.NotFoundFailure;
import com.pad.xmen.ale.models.History;
import com.pad.xmen.ale.models.Player;
import com.pad.xmen.ale.models.Room;
import com.pad.xmen.ale.persistence.RoomDAO;
import com.pad.xmen.ale.persistence.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
@RestController
public class HttpController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/room")
    private List<Room> getAllRooms() {
        List<RoomDAO> roomDaos = roomRepository.findAll();
        List<Room> rooms = new ArrayList<>();
        roomDaos.forEach(roomDAO -> {
            List<Player> players = new ArrayList<>();
            roomDAO.getPlayers().forEach(playerDAO -> {
                players.add(new Player(playerDAO.getName(), playerDAO.getScore(), playerDAO.getOwner()));
            });
            rooms.add(new Room(roomDAO.getId(), players, roomDAO.getCreatedAt(), roomDAO.getStartedAt(), roomDAO.getFinishedAt()));
        });
        return rooms;
    }

    @GetMapping("/room/{roomId}")
    private Room getRoom(@PathVariable UUID roomId) {
        RoomDAO roomDAO = roomRepository.findById(roomId).orElseThrow(
                () -> new NotFoundFailure("Room with id '" + roomId + "' was not found")
        );

        List<Player> players = new ArrayList<>();
        roomDAO.getPlayers().forEach(playerDAO -> {
            players.add(new Player(playerDAO.getName(), playerDAO.getScore(), playerDAO.getOwner()));
        });

        return new Room(roomDAO.getId(), players, roomDAO.getCreatedAt(), roomDAO.getStartedAt(), roomDAO.getFinishedAt());
    }

    @GetMapping("/room/{roomId}/history")
    private List<History> getRoomHistory(@PathVariable UUID roomId) {
        RoomDAO roomDAO = roomRepository.findById(roomId).orElseThrow(
                () -> new NotFoundFailure("Room with id '" + roomId + "' was not found")
        );
        List<History> histories = new ArrayList<>();
        roomDAO.getHistory().forEach(historyDAO -> {
            histories.add(new History(historyDAO.getEventKey(), historyDAO.getEventValue(), historyDAO.getAt()));
        });

        return histories;
    }
}
