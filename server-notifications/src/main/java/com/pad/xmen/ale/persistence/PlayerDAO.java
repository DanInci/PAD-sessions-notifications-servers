package com.pad.xmen.ale.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-20
 */
@Embeddable
@Table(name = "players")
public class PlayerDAO {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "score", columnDefinition = "int default 0")
    private Integer score;

    @Column(name = "is_owner", columnDefinition = "boolean default 0")
    private Boolean isOwner;

    public PlayerDAO() {
    }

    public PlayerDAO(String name, Integer score, Boolean isOwner) {
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
