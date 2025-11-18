package com.incident.management.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "incidents")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IncidentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String incidentId; 

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private UserEntity reporter; 

    @Column(length = 1000)
    private String description;

    @NotBlank(message = "Priority must be provided (High, Medium, or Low)")
    private String priority; // High, Medium, Low
    
    private String status;   // Open, In Progress, Closed
    
    @NotBlank(message = "Type must be provided (Enterprise or Government)")
    private String type;     // Enterprise or Government

    private LocalDateTime reportedAt;
}

