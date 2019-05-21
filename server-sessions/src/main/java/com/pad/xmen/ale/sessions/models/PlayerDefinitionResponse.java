package com.pad.xmen.ale.sessions.models;

import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
public class RoomDefinitionResponse {

    private String token;
    private UUID roomId;
    private String dashboardUrl;

    public RoomDefinitionResponse() {
    }

    public RoomDefinitionResponse(String token, UUID roomId, String dashboardUrl) {
        this.token = token;
        this.roomId = roomId;
        this.dashboardUrl = dashboardUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public String getDashboardUrl() {
        return dashboardUrl;
    }

    public void setDashboardUrl(String dashboardUrl) {
        this.dashboardUrl = dashboardUrl;
    }
}
