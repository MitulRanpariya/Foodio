package com.example.foodorderback.service;

import java.util.List;

import com.example.foodorderback.dto.MealDTO;
import com.example.foodorderback.model.Meal;

public interface MealService {

    String isValidInput(Meal meal);
    List<MealDTO> findAll();
    String save(Meal meal);
    String delete(Long mealId);
    Meal findOne(Long id);
    String editMeal(Meal meal);
    String editMeal(Meal meal, org.springframework.web.multipart.MultipartFile image);
    List<MealDTO> getMealsByMealTypeId(Long mealTypeId);
}
