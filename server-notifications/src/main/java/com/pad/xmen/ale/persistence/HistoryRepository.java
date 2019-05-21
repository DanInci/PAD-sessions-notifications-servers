package com.pad.xmen.ale.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
@Component
@Repository
public interface HistoryRepository extends JpaRepository<HistoryDAO, UUID> {
}
