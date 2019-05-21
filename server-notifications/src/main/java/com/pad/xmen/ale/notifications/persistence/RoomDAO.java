package com.pad.xmen.ale.notifications.persistence;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-20
 */
@Entity
@Table(name = "rooms")
public class RoomDAO {

    @Id
    @Column(name = "id")
    private UUID id;

    @OrderBy("name ASC")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "room")
    private List<PlayerDAO> players;

    @OrderBy("at ASC")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "room")
    private List<HistoryDAO> history;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    public RoomDAO() {
    }

    public RoomDAO(UUID id, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<PlayerDAO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDAO> players) {
        this.players = players;
    }

    public List<HistoryDAO> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryDAO> history) {
        this.history = history;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }
}
