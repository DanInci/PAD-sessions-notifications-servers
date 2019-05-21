package com.pad.xmen.ale.notifications.persistence;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-20
 */
@Entity
@Table(name = "players")
public class PlayerDAO {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private RoomDAO room;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "score", columnDefinition = "int default 0")
    private Integer score;

    @Column(name = "is_owner", columnDefinition = "boolean default 0")
    private Boolean isOwner;

    public PlayerDAO() {
    }

    public PlayerDAO(UUID id, RoomDAO room, String name, Integer score, Boolean isOwner) {
        this.id = id;
        this.room = room;
        this.name = name;
        this.score = score;
        this.isOwner = isOwner;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }
}
