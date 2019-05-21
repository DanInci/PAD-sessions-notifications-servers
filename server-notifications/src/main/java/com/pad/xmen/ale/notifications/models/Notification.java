package com.pad.xmen.ale.notifications.models;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-20
 */
public class Notification {

    private UUID roomId;

    private EventKey eventKey;

    private String parameters;

    private LocalDateTime at;

    public Notification(UUID roomId, EventKey eventKey, String parameters, LocalDateTime at) {
        this.roomId = roomId;
        this.eventKey = eventKey;
        this.parameters = parameters;
        this.at = at;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public EventKey getEventKey() {
        return eventKey;
    }

    public void setEventKey(EventKey eventKey) {
        this.eventKey = eventKey;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public LocalDateTime getAt() {
        return at;
    }

    public void setAt(LocalDateTime at) {
        this.at = at;
    }
}
