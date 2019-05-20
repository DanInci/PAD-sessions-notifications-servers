package com.pad.teamx.models;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-20
 */
public class Event {

    private EventKey key;

    private String parameters;

    public Event(EventKey key, String parameters) {
        this.key = key;
        this.parameters = parameters;
    }

    public EventKey getKey() {
        return key;
    }

    public void setKey(EventKey key) {
        this.key = key;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return key + " with " + parameters;
    }
}
