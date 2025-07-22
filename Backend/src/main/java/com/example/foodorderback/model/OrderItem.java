package com.example.foodorderback.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private Meal meal;

	private String mealName;
	private int mealPrice;
	private String mealDescription;

	@Lob
	private String mealImage;

	private String mealImageName;
	private String mealTypeName;

	@ManyToOne
	private FinalOrder finalOrder;

	private int quantity;
}
