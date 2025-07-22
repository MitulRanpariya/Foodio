package com.example.foodorderback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
	private List<ItemFromCartDTO> itemsFromCart;
	private String address;
	private String phoneNumber;
	private int finalPrice;
}
