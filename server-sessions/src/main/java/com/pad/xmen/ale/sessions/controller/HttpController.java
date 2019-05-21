package com.pad.xmen.ale.sessions.controller;

import com.pad.xmen.ale.sessions.models.Event;
import com.pad.xmen.ale.sessions.models.EventKey;
import com.pad.xmen.ale.sessions.models.PlayerDefinition;
import com.pad.xmen.ale.sessions.models.PlayerDefinitionResponse;
import com.pad.xmen.ale.sessions.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
@RestController
public class HttpController {

    @Value("${dashboard.url}")
    private String dashboardUrl;

    @Autowired
    private SocketHandler socket;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/")
    public ResponseEntity checkConnection() {
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/room")
    public ResponseEntity<PlayerDefinitionResponse> createRoom(@RequestBody PlayerDefinition definition) {
        UUID roomId = UUID.randomUUID();
        Event event = new Event(EventKey.CREATE, definition.getName());

        socket.sendEvent(roomId, event);

        String token = jwtUtil.generateToken(definition.getName(), roomId, true);
        String dashboardLink = dashboardUrl + "/" + roomId;
        PlayerDefinitionResponse response = new PlayerDefinitionResponse(token, roomId, dashboardLink);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/room/{roomId}")
    public PlayerDefinitionResponse joinRoom(@PathVariable UUID roomId, @RequestBody PlayerDefinition definition) {
        Event event = new Event(EventKey.JOIN, definition.getName()); //check if name is not already joined

        socket.sendEvent(roomId, event);

        String token = jwtUtil.generateToken(definition.getName(), roomId, false);
        String dashboardLink = dashboardUrl + "/" + roomId;
        return new PlayerDefinitionResponse(token, roomId, dashboardLink);
    }


}
