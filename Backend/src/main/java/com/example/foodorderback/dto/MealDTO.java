package com.example.foodorderback.dto;

import com.example.foodorderback.model.Meal;
import com.example.foodorderback.model.MealType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MealDTO {

	private Long id;
	private MealType mealType;
	private String name;
	private int price;
	private String image;
	private String imageName;
	private String description;

	public MealDTO(Meal meal) {
		this.id = meal.getId();
		this.mealType = meal.getMealType();
		this.name = meal.getName();
		this.price = meal.getPrice();
		this.image = meal.getImage();
		this.imageName = meal.getImageName();
		this.description = meal.getDescription();
	}
}
