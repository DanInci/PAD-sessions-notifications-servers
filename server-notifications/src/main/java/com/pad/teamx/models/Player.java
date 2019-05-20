package com.pad.teamx.models;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
public class Player {

    private String name;

    private Integer score;

    private Boolean isOwner;

    public Player() {
    }

    public Player(String name, Integer score, Boolean isOwner) {
        this.name = name;
        this.score = score;
        this.isOwner = isOwner;
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
