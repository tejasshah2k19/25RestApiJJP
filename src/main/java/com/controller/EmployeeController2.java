package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.entity.EmployeeEntity;
import com.repository.EmployeeRepository;
import com.util.JwtUtil;

@RestController
public class EmployeeController2 {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	JwtUtil jwt;

	// add employee
	@PostMapping("/admin/employees")
	public ResponseEntity<?> addEmployee(@RequestHeader String token, @RequestBody EmployeeEntity employeeEntity) {

		System.out.println(token);

		if (!jwt.isTokenValid(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(employeeEntity);
		}

		String email = jwt.extractUsername(token);

		// db getEmail -> user -> role -> admin -> go ahead

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

	@GetMapping("admin/employees")
	public ResponseEntity<?> getAllEmployees(@RequestHeader String token) {

		// token
		System.out.println(token);

		Optional<EmployeeEntity> db = employeeRepository.findByToken(token);

		if (db.isPresent()) {
			List<EmployeeEntity> employees = employeeRepository.findAll();
			return ResponseEntity.ok(employees);
		} else {
			HashMap<String, String> map = new HashMap<>();

			map.put("token", token);
			map.put("msg", "Invalid token");

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);

		}
	}
}
