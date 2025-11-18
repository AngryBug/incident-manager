package com.incident.management.service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incident.management.controller.IncidentController;
import com.incident.management.entity.IncidentEntity;
import com.incident.management.entity.UserEntity;
import com.incident.management.repository.IncidentEntityRepository;
import com.incident.management.repository.UserEntityRepository;

@Service
public class IncidentService {

    @Autowired
    private IncidentEntityRepository incidentEntityRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(IncidentService.class);

    public IncidentEntity createIncident(String userEmail, IncidentEntity incident) {
    	
    	logger.info("incident:"+incident);
    	
    	if (!List.of("High", "Medium", "Low").contains(incident.getPriority())) {
            throw new RuntimeException("Priority must be one of: High, Medium, Low");
        }
        if (!List.of("Enterprise", "Government").contains(incident.getType())) {
            throw new RuntimeException("Type must be either Enterprise or Government");
        }
    	
        UserEntity reporter = userEntityRepository.findByEmail(userEmail).orElseThrow();
        incident.setReporter(reporter);

        String uniqueId;
        do {
            uniqueId = "RMG" + String.format("%05d", new Random().nextInt(99999)) + Year.now().getValue();
        } while (incidentEntityRepository.findByIncidentId(uniqueId).isPresent());

        incident.setIncidentId(uniqueId);
        incident.setStatus("Open");
        incident.setReportedAt(LocalDateTime.now());
        return incidentEntityRepository.save(incident);
    }

    public List<IncidentEntity> getMyIncidents(String userEmail) {
    	UserEntity user = userEntityRepository.findByEmail(userEmail).orElseThrow();
        return incidentEntityRepository.findByReporter(user);
    }

    public Optional<IncidentEntity> getIncidentById(String incidentId, String userEmail) {
        return incidentEntityRepository.findByIncidentId(incidentId)
                .filter(i -> i.getReporter().getEmail().equals(userEmail));
    }

    public IncidentEntity updateIncident(String userEmail, String incidentId, IncidentEntity updated) {
    	IncidentEntity incident = incidentEntityRepository.findByIncidentId(incidentId)
                .orElseThrow(() -> new RuntimeException("Incident not found"));

        if (!incident.getReporter().getEmail().equals(userEmail)) {
            throw new RuntimeException("Access denied");
        }
        if ("Closed".equalsIgnoreCase(incident.getStatus())) {
            throw new RuntimeException("Closed incidents cannot be edited");
        }

        incident.setDescription(updated.getDescription());
        incident.setPriority(updated.getPriority());
        incident.setStatus(updated.getStatus());
        incident.setType(updated.getType());

        return incidentEntityRepository.save(incident);
    }

    public Optional<IncidentEntity> searchIncident(String incidentId) {
    	logger.info("search:"+incidentId);
        return incidentEntityRepository.findByIncidentId(incidentId);
    }
}

