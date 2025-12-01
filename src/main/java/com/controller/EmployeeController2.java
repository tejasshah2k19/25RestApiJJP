package com.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.EmployeeEntity;
import com.repository.EmployeeRepository;

@RestController
public class EmployeeController2 {

	@Autowired
	EmployeeRepository employeeRepository;

	// add employee
	@PostMapping("/admin/employees")
	public ResponseEntity<?> addEmployee(@RequestBody EmployeeEntity employeeEntity) {
		if (employeeEntity.getEmail() == null || employeeEntity.getEmail().isBlank()) {
//			return employeeEntity;

			// object { employeeEntity,error}
			// employeeEntity , errors
			// Map
			Map<String, Object> map = new HashMap<>();
			map.put("data", employeeEntity);
			map.put("error", "Please Enter Email");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
		employeeRepository.save(employeeEntity);
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeEntity);
	}
}
