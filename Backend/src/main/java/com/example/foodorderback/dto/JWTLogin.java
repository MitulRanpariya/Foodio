package com.example.foodorderback.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JWTLogin {
	private String username;
	private String role;
}
