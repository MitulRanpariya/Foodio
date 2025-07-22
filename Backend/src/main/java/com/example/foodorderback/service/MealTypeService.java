package com.example.foodorderback.service;

import java.util.List;

import com.example.foodorderback.dto.MealTypeDTO;
import com.example.foodorderback.model.MealType;

public interface MealTypeService {

    List<MealTypeDTO> getAllMealTypes();
    String isValidInput(MealType mealType);
    String save(MealType mealType);
    String editMealType(MealType mealType);
    String editMealTypeWithImage(MealType mealType);
    MealType findOne(Long id);
    String delete(Long mealTypeId);
}
