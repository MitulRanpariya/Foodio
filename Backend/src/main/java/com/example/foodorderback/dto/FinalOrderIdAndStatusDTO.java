package com.example.foodorderback.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FinalOrderIdAndStatusDTO {
	private Long activeOrderId;
	private String status;
}
