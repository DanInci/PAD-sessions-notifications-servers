package com.pad.xmen.ale.persistence;

import com.pad.xmen.ale.models.EventKey;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-20
 */
@Embeddable
@Table(name = "history")
public class HistoryDAO {

    @Column(name = "event_key")
    @Enumerated(value = EnumType.STRING)
    private EventKey eventKey;

    @Column(name = "event_value")
    private String eventValue;

    @Column(name = "at")
    private LocalDateTime at;

    public HistoryDAO() {
    }

    public HistoryDAO(EventKey eventKey, String eventValue, LocalDateTime at) {
        this.eventKey = eventKey;
        this.eventValue = eventValue;
        this.at = at;
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
}
