package com.pad.xmen.ale.sessions.models;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
public class RoomDefinition {

    private String name;

    public RoomDefinition() {
    }

    public RoomDefinition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
