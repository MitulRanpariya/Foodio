package com.example.foodorderback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MealType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String typeName;

	
	private String image;

	private String imageName;
	private String description;
	private boolean isDeleted;

	// Uncomment below if meal relationship is needed in the future
	@JsonIgnore
	@OneToMany(mappedBy = "mealType", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Meal> meals = new ArrayList<>();
}
