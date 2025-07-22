package com.example.foodorderback.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.foodorderback.dto.MealDTO;
import com.example.foodorderback.model.Meal;
import com.example.foodorderback.service.MealService;
import com.google.gson.Gson;

@CrossOrigin("*")
@RestController
@RequestMapping("api/meal")
public class MealController {

	@Autowired
	private MealService mealService;

	@GetMapping("/getAllMeals")
	public ResponseEntity<List<MealDTO>> getAllMeals() {
		return ResponseEntity.ok(mealService.findAll());
	}

	@GetMapping("/getMealsByMealTypeId/{id}")
	public ResponseEntity<List<MealDTO>> getMealsByMealTypeId(@PathVariable Long id) {
		return ResponseEntity.ok(mealService.getMealsByMealTypeId(id));
	}

	@PostMapping(value = "/createMeal", consumes = "multipart/form-data")
	public ResponseEntity<String> createMeal(@RequestParam("image") MultipartFile image, HttpServletRequest request) {
		Meal meal = new Gson().fromJson(request.getParameter("meal"), Meal.class);
		String validation = mealService.isValidInput(meal);
		if (!"valid".equals(validation)) return ResponseEntity.ok(validation);

		try {
			meal.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
			meal.setImageName(image.getOriginalFilename());
			return ResponseEntity.ok(mealService.save(meal));
		} catch (Exception e) {
			return ResponseEntity.ok("fail");
		}
	}

	// @PutMapping(value = "/updateMeal", consumes = "application/json", produces = "application/json")
	// public ResponseEntity<String> editMeal(@RequestBody Meal meal) {
	// 	return ResponseEntity.ok(mealService.editMeal(meal));
	// }
	@PutMapping(value = "/updateMeal", consumes = "multipart/form-data")
public ResponseEntity<String> editMeal(
    @RequestParam("meal") String mealJson,
    @RequestParam(value = "image", required = false) MultipartFile image
) {
    Meal meal = new Gson().fromJson(mealJson, Meal.class);
    String validation = mealService.isValidInput(meal);
    if (!"valid".equals(validation)) return ResponseEntity.ok("invalid");

    try {
        return ResponseEntity.ok(mealService.editMeal(meal, image));
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.ok("fail");
    }
}

	@PutMapping("/deleteMeal/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		return ResponseEntity.ok(mealService.delete(id));
	}
}
