package com.incident.management.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incident.management.entity.IncidentEntity;
import com.incident.management.entity.UserEntity;

public interface IncidentEntityRepository extends JpaRepository<IncidentEntity, Long> {
    List<IncidentEntity> findByReporter(UserEntity reporter);
    Optional<IncidentEntity> findByIncidentId(String incidentId);
}

