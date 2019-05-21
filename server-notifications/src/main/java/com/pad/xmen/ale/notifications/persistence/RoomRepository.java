package com.pad.xmen.ale.notifications.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-20
 */
@Component
@Repository
public interface RoomRepository extends JpaRepository<RoomDAO, UUID> {
    List<RoomDAO> findAllByFinishedAtBefore(LocalDateTime time);
}
