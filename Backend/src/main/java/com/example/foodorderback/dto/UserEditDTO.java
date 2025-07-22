package com.example.foodorderback.dto;

import com.example.foodorderback.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String phoneNumber;
	private String address;

	public UserEditDTO(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.phoneNumber = user.getPhoneNumber();
		this.address = user.getAddress();
	}
}
