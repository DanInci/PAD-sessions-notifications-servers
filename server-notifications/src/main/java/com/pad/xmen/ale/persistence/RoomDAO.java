package com.pad.xmen.ale.persistence;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-20
 */
@Entity
@Table(name = "game_rooms")
public class RoomDAO {

    @Id
    @Column(name = "id")
    private UUID id;

    @ElementCollection
    @CollectionTable(name="Players", joinColumns=@JoinColumn(name="roomId"))
    private List<PlayerDAO> players;

    @ElementCollection
    @CollectionTable(name="History", joinColumns=@JoinColumn(name="roomId"))
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
