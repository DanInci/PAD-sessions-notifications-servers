package com.pad.xmen.ale.models;

import java.time.LocalDateTime;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
public class History {

    private EventKey eventKey;

    private String eventValue;

    private LocalDateTime now;

    public History() {
    }

    public History(EventKey eventKey, String eventValue, LocalDateTime now) {
        this.eventKey = eventKey;
        this.eventValue = eventValue;
        this.now = now;
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

    public LocalDateTime getNow() {
        return now;
    }

    public void setNow(LocalDateTime now) {
        this.now = now;
    }
}
