package com.learning.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.entity.Login;
import com.learning.entity.Reg;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.service.LoginService;
import com.learning.service.RegService;

@RestController
@RequestMapping
public class RegController {

	@Autowired
	RegService regService;

	@Autowired
	LoginService loginService;

	@PostMapping("/register")
	public ResponseEntity<?> addUser(@Valid @RequestBody Reg reg) throws AlreadyExistsException {

		Reg result = regService.addReg(reg);
		System.out.println(result);
		return ResponseEntity.status(201).body(result);

	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody Login login) {
		String result = loginService.authenticate(login);
		if (result.equals("success")) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", "Success");
			return ResponseEntity.status(200).body(map);
		} else {
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", "Fail");
			return ResponseEntity.status(403).body(map);
		}

	}

	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {
		Reg[] regs = regService.getAllReg();
		if (regs.length == 0) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", "No Record Found");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		}
		return ResponseEntity.status(200).body(regs);
	}

	@GetMapping("/users/{regId}")
	public ResponseEntity<?> getUserById(@PathVariable("regId") int regId)
			throws IdNotFoundException {
		Reg reg = regService.getRegById(regId);
		return ResponseEntity.status(200).body(reg);
	}

	@PutMapping("/update/users")
	public ResponseEntity<?> updateUserById(@RequestBody Reg reg)
			throws IdNotFoundException, AlreadyExistsException {
		Reg result = regService.updateReg(reg);
		return ResponseEntity.status(200).body(result);
	}

	@DeleteMapping("/delete/{regId}")
	public ResponseEntity<?> deleteUserById(@PathVariable("regId") int regId)
			throws IdNotFoundException {
		String result = regService.deleteRegById(regId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", result);
		return ResponseEntity.status(201).body(map);

	}

}
