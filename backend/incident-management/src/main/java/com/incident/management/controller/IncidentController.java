package com.incident.management.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incident.management.entity.IncidentEntity;
import com.incident.management.service.IncidentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/incidents")
public class IncidentController {

    @Autowired
    private IncidentService incidentService;
    
    private static final Logger logger = LoggerFactory.getLogger(IncidentController.class);

    @PostMapping("/create")
    public IncidentEntity createIncident(@Valid @RequestBody IncidentEntity incident, Authentication authentication) {
        String email = authentication.getName();
        logger.info("name:"+authentication.getName());
        return incidentService.createIncident(email, incident);
    }

    @GetMapping("/my")
    public List<IncidentEntity> getMyIncidents(Authentication authentication) {
        return incidentService.getMyIncidents(authentication.getName());
    }

    @GetMapping("/{incidentId}")
    public IncidentEntity getIncident(@PathVariable String incidentId, Authentication authentication) {
        return incidentService.getIncidentById(incidentId, authentication.getName())
                .orElseThrow(() -> new RuntimeException("Incident not found or access denied"));
    }

    @PutMapping("/{incidentId}")
    public IncidentEntity updateIncident(@PathVariable String incidentId,
                                   @RequestBody IncidentEntity updated,
                                   Authentication authentication) {
        return incidentService.updateIncident(authentication.getName(), incidentId, updated);
    }

    @GetMapping("/search")
    public IncidentEntity searchIncident(@RequestParam String incidentId) {
        return incidentService.searchIncident(incidentId)
                .orElseThrow(() -> new RuntimeException("Incident Not Found"));
    }
}

