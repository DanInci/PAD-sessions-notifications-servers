package com.pad.xmen.ale.persistence;

import com.pad.xmen.ale.models.EventKey;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-20
 */
@Entity
@Table(name = "history")
public class HistoryDAO implements Comparable<HistoryDAO> {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private RoomDAO room;

    @Column(name = "event_key")
    @Enumerated(value = EnumType.STRING)
    private EventKey eventKey;

    @Column(name = "event_value")
    private String eventValue;

    @Column(name = "at")
    private LocalDateTime at;

    public HistoryDAO() {
    }

    public HistoryDAO(UUID id, RoomDAO room, EventKey eventKey, String eventValue, LocalDateTime at) {
        this.id = id;
        this.room = room;
        this.eventKey = eventKey;
        this.eventValue = eventValue;
        this.at = at;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RoomDAO getRoom() {
        return room;
    }

    public void setRoom(RoomDAO room) {
        this.room = room;
    }

    public EventKey getEventKey() {
        return eventKey;
    }

    public void setEventKey(EventKey eventKey) {
        this.eventKey = eventKey;
    }

    public String getEventValue() {
        return eventValue;
    }

    public void setEventValue(String eventValue) {
        this.eventValue = eventValue;
    }

    public LocalDateTime getAt() {
        return at;
    }

    public void setAt(LocalDateTime at) {
        this.at = at;
    }

    @Override
    public int compareTo(HistoryDAO o) {
        return this.getAt().isBefore(o.getAt()) ? 1: -1;
    }
}
