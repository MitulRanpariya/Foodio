package com.example.foodorderback.dto;

import java.util.Date;

import com.example.foodorderback.model.FinalOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinalOrderDTO {

	private Long id;
	private String phoneNumber;
	private Date date;
	private int finalPrice;
	private String address;
	private String status;

	public FinalOrderDTO(FinalOrder finalOrder) {
		this.id = finalOrder.getId();
		this.address = finalOrder.getAddress();
		this.phoneNumber = finalOrder.getPhoneNumber();
		this.date = finalOrder.getDate();
		this.finalPrice = finalOrder.getFinalPrice();
		this.status = finalOrder.getStatus();
	}
}
