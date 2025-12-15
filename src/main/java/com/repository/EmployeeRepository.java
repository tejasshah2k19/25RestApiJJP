package com.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.EmployeeEntity;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {
	// findByXXX(); // XXX => any field of EmployeeEntity

	Optional<EmployeeEntity> findByEmail(String email);

	Optional<EmployeeEntity> findByFirstName(String firstName);

	Optional<EmployeeEntity> findByFirstNameAndLastName(String firstName, String lastName);

	Optional<EmployeeEntity> findByToken(String token);

}
