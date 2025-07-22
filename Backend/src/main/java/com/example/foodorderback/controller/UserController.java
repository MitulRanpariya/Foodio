package com.example.foodorderback.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.foodorderback.dto.PasswordDTO;
import com.example.foodorderback.dto.UserDTO;
import com.example.foodorderback.model.User;
import com.example.foodorderback.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/registration", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> createUser(@RequestBody User user) {
		String res = userService.validateUser(user);
		if (!"valid".equals(res)) return ResponseEntity.ok(res);
		return ResponseEntity.ok(userService.saveUser(user));
	}

	@PostMapping(value = "/createEmployee", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> createEmployee(@RequestBody User user) {
		String res = userService.validateUser(user);
		if (!"valid".equals(res)) return ResponseEntity.ok(res);
		return ResponseEntity.ok(userService.saveEmployee(user));
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		return ResponseEntity.ok(userService.findAllUsers());
	}

	@GetMapping("/getAllEmployees")
	public ResponseEntity<List<UserDTO>> getAllEmployees() {
		return ResponseEntity.ok(userService.findAllEmployees());
	}

	@PutMapping(value = "/updateUser", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO) {
		String valid = userService.validateUserUpdate(userDTO);
		if (!"valid".equals(valid)) return ResponseEntity.ok(valid);
		return ResponseEntity.ok(userService.updateUser(userDTO));
	}

	@PutMapping(value = "/updateUserByIdAndDetails/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> updateUserByIdAndDetails(@PathVariable Long id, @RequestBody User details) {
		User existing = userService.findOne(id);
		if (existing == null) return ResponseEntity.notFound().build();
		details.setId(existing.getId());
		details.setRole(existing.getRole());
		String valid = userService.validateEmployeeUpdate(details);
		if (!"valid".equals(valid)) return ResponseEntity.ok(valid);
		return ResponseEntity.ok(userService.updateEmployee(details));
	}

	@GetMapping("/getCurrentUser")
	public ResponseEntity<User> getCurrentUser() {
		return ResponseEntity.ok(userService.getCurrentUser());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(new UserDTO(user));
	}

	@PutMapping(value = "/deactivateUser/{id}", produces = "application/json")
	public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
		if (userService.findOne(id) == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(userService.deactivateUser(id));
	}

	@PutMapping(value = "/changePassword", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> changePassword(@RequestBody PasswordDTO dto) {
		return ResponseEntity.ok(userService.changePassword(dto));
	}

	//email
	@GetMapping("/verify")
public ResponseEntity<String> verifyEmail(@RequestParam String token) {
    User user = userService.findByToken(token);
    if (user == null) return ResponseEntity.badRequest().body("Invalid token");

    user.setEmailVerified(true);
    user.setEmailVerificationToken(null);
    userService.saveUpdatedUser(user); // Create a new method to just save user
    return ResponseEntity.status(HttpStatus.FOUND)
        .location(URI.create("http://localhost:3000/email-verified"))
        .build();

}

}
