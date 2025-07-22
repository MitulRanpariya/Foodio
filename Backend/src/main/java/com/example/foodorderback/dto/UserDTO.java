package com.example.foodorderback.dto;

import com.example.foodorderback.model.Role;
import com.example.foodorderback.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private Role role;
	private String phoneNumber;
	private String address;
	private boolean deleted;
	private String password;

	public UserDTO(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.role = user.getRole();
		this.phoneNumber = user.getPhoneNumber();
		this.address = user.getAddress();
		this.deleted = user.isDeleted();
	}
}
