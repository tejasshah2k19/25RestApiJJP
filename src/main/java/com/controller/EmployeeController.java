package com.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.EmployeeEntity;
import com.repository.EmployeeRepository;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	// REST client -> postman
	// input , output => json

	// add employee
	@PostMapping("/employees")
	public ResponseEntity<?> addEmployee(@Validated @RequestBody EmployeeEntity employeeEntity, BindingResult result) {

		Map<String, Object> map = new HashMap<>();

		// validation
		if (result.hasErrors()) {
			HashMap<String, String> errorMap = new HashMap<>(); 
			
			 for(FieldError f: result.getFieldErrors()) {
				 errorMap.put(f.getField(), f.getDefaultMessage());
			 }
			map.put("errors", errorMap);
			map.put("data", employeeEntity);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}

		// email is present or not

//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);

		Optional<EmployeeEntity> op = employeeRepository.findByEmail(employeeEntity.getEmail());
		if (op.isPresent()) {
			map.put("data", employeeEntity);
	 
			Map<String, Object> errorMap = new HashMap<>();
			errorMap.put("email", "Duplicate Email");
			map.put("errors", errorMap);

			return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
		} else {

			// pre set info
			employeeEntity.setCreatedAt(LocalDate.now());

			// dao - repository - insert
			employeeRepository.save(employeeEntity);

			map.put("data", employeeEntity);
			map.put("msg", "signup done XXXX ");
			return ResponseEntity.status(HttpStatus.CREATED).body(map);
		}
	}
	// get all employee

	@GetMapping("/employees")
	public List<EmployeeEntity> getAllEmployees() {
		List<EmployeeEntity> employees = employeeRepository.findAll();
		return employees;
	}

	// remove employee by employeeId
	@DeleteMapping("/employees/{employeeId}")
	public EmployeeEntity DeleteEmployeeById(@PathVariable UUID employeeId) {
		Optional<EmployeeEntity> op = employeeRepository.findById(employeeId);
		if (op.isEmpty()) {
			return null;
		} else {
			EmployeeEntity emp = op.get();
			employeeRepository.deleteById(employeeId);

			return emp;// extract optional entity
		}
	}

	// get Single employee Data By Id
	@GetMapping("/employees/{employeeId}")
	public EmployeeEntity getEmployeeById(@PathVariable UUID employeeId) {
		Optional<EmployeeEntity> op = employeeRepository.findById(employeeId);
		if (op.isEmpty()) {
			return null;
		} else {
			return op.get();// extract optional entity
		}
	}

	// update employee data
	@PutMapping("/employees")
	public EmployeeEntity updateEmployeeById(@RequestBody EmployeeEntity employeeEntity) {

		Optional<EmployeeEntity> op = employeeRepository.findById(employeeEntity.getEmployeeId());
		if (op.isEmpty()) {
			return null;
		} else {
			EmployeeEntity dbEmp = op.get();
			// update
			dbEmp.setFirstName(employeeEntity.getFirstName());
			dbEmp.setLastName(employeeEntity.getLastName());
			employeeRepository.save(dbEmp);

			return dbEmp;// extract optional entity
		}

	}

}
