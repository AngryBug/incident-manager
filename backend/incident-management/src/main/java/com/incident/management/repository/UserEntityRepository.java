package com.incident.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incident.management.entity.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long>  {
	
	Optional<UserEntity> findByEmail(String email);

}
