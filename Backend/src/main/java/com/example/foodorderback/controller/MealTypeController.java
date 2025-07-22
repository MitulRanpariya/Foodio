package com.example.foodorderback.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.foodorderback.dto.MealTypeDTO;
import com.example.foodorderback.model.MealType;
import com.example.foodorderback.service.MealTypeService;
import com.google.gson.Gson;

@CrossOrigin("*")
@RestController
@RequestMapping("api/mealType")
public class MealTypeController {

	@Autowired
	private MealTypeService mealTypeService;

	@GetMapping("/getAllMealTypes")
	public ResponseEntity<List<MealTypeDTO>> getAllMealTypes() {
		return ResponseEntity.ok(mealTypeService.getAllMealTypes());
	}

	@PostMapping(value = "/createMealType", consumes = "multipart/form-data")
	public ResponseEntity<String> createMealType(@RequestParam("image") MultipartFile image, HttpServletRequest request) {
		MealType mealType = new Gson().fromJson(request.getParameter("mealType"), MealType.class);
		String validation = mealTypeService.isValidInput(mealType);
		if (!"valid".equals(validation)) return ResponseEntity.ok("invalid");

		try {
			mealType.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
			mealType.setImageName(image.getOriginalFilename());
			return ResponseEntity.ok(mealTypeService.save(mealType));
		} catch (IOException e) {
			return ResponseEntity.ok("fail");
		}
	}

	@PutMapping(value = "/updateMealType", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> updateMealType(@RequestBody MealType mealType) {
		return ResponseEntity.ok(mealTypeService.editMealType(mealType));
	}

	@PutMapping(value = "/updateMealTypeWithImage", consumes = "multipart/form-data")
	public ResponseEntity<String> updateMealTypeWithImage(
		@RequestParam(value = "image", required = false) MultipartFile image,
		HttpServletRequest request
	) {
		MealType mealType = new Gson().fromJson(request.getParameter("mealType"), MealType.class);
		String validation = mealTypeService.isValidInput(mealType);
		if (!"valid".equals(validation)) return ResponseEntity.ok("invalid");

		try {
			if (image != null && !image.isEmpty()) {
				mealType.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
				mealType.setImageName(image.getOriginalFilename());
			}
			return ResponseEntity.ok(mealTypeService.editMealTypeWithImage(mealType));
		} catch (IOException e) {
			return ResponseEntity.ok("fail");
		}
	}

	@PutMapping("/deleteMealType/{id}")
	public ResponseEntity<String> deleteMealType(@PathVariable Long id) {
		return ResponseEntity.ok(mealTypeService.delete(id));
	}
}
