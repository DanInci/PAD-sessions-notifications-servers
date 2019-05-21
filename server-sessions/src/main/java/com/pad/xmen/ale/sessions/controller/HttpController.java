package com.pad.xmen.ale.controller;

import com.pad.xmen.ale.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
@RestController
public class HttpController {

    private UUID id = UUID.randomUUID();

    @Autowired
    private SocketHandler socket;

    @GetMapping("/")
    public ResponseEntity checkConnection() {
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/event")
    public void sendEvent(@RequestBody Event event) {
        socket.sendEvent(id, event);
    }


}
