package com.example.foodorderback.controller;

import com.example.foodorderback.dto.LoginDTO;
import com.example.foodorderback.model.Login;
import com.example.foodorderback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/welcomeTest")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("test!!!");
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LoginDTO> generateToken(@RequestBody Login login) {
        LoginDTO loginDTO = userService.generateToken(login);
        return ResponseEntity.ok(loginDTO);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        String responseToClient = userService.isValidLogout();
        return ResponseEntity.ok(responseToClient);
    }
}
