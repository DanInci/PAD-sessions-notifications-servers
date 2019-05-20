package com.pad.teamx.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-20
 */
public interface RoomRepository extends JpaRepository<RoomDAO, UUID> {
}
