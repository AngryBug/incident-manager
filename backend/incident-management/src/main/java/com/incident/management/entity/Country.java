package com.incident.management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "country_master")
@Data
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; 

    @Column(nullable = false, unique = true, length = 3)
    private String code;
}
