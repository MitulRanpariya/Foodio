package com.example.foodorderback.dto;

import java.util.Date;

import com.example.foodorderback.model.FinalOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FinalOrderNotLoggedDTO {

	private Long id;
	private String phoneNumber;
	private Date date;
	private int finalPrice;
	private String address;
	private String status;

	public FinalOrderNotLoggedDTO(FinalOrder finalOrder) {
		this.id = finalOrder.getId();
		this.phoneNumber = finalOrder.getPhoneNumber();
		this.finalPrice = finalOrder.getFinalPrice();
		this.date = finalOrder.getDate();
		this.address = finalOrder.getAddress();
		this.status = finalOrder.getStatus();
	}
}
