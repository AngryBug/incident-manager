package com.incident.management.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incident.management.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByNameIgnoreCase(String name);
}
