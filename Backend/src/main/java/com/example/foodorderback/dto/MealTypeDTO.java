package com.example.foodorderback.dto;

import com.example.foodorderback.model.MealType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealTypeDTO {
	private Long id;
	private String typeName;
	private String image;
	private String imageName;
	private String description;

	public MealTypeDTO(MealType mealType) {
		this.id = mealType.getId();
		this.typeName = mealType.getTypeName();
		this.image = mealType.getImage();
		this.imageName = mealType.getImageName();
		this.description = mealType.getDescription();
	}
}
