package com.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "employees1")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID employeeId;
	
	@NotBlank(message = "Please Enter FirstName")
	String firstName;

	@NotBlank(message = "Please Enter LastName")
	String lastName;
	String email;
	String password;
	String token; 
	
	LocalDate createdAt;
	
	public EmployeeEntity() {
		createdAt = LocalDate.now(); 
	}

}
