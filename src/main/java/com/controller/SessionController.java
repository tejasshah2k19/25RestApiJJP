package com.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.EmployeeEntity;
import com.repository.EmployeeRepository;
import com.util.JwtUtil;

@RestController
@RequestMapping("/public")
public class SessionController {

	@Autowired
	JwtUtil jwt;

	@Autowired
	EmployeeRepository employeeRepository;

	String data = "0123456789qwertyuioplkjhgfdsazxcvbnmPOIUYTREWQASDFGHJKLMNBVCXZ";

	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody EmployeeEntity employeeEntity) {
		StringBuffer sb = new StringBuffer();

		Optional<EmployeeEntity> op = employeeRepository.findByEmail(employeeEntity.getEmail());
		if (op.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(employeeEntity);
		}
		EmployeeEntity db = op.get();

//		for(int i=1;i<=32;i++) {
//			int index = (int)(Math.random()*data.length()); //0-61 
//			sb.append(data.charAt(index)); 
//		} 

		String token = jwt.generateToken(employeeEntity.getEmail());

		db.setToken(token);
//		employeeRepository.save(db); //update -> token  
		// email and password correct
		return ResponseEntity.status(HttpStatus.OK).body(db);
	}

	@PostMapping("signup")
	public ResponseEntity<?> signup() {
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
