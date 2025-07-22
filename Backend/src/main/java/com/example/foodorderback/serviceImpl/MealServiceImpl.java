package com.example.foodorderback.serviceImpl;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.foodorderback.dto.MealDTO;
import com.example.foodorderback.model.Meal;
import com.example.foodorderback.repository.MealRepository;
import com.example.foodorderback.repository.MealTypeRepository;
import com.example.foodorderback.service.MealService;
import com.example.foodorderback.model.MealType;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private MealTypeRepository mealTypeRepository;

    @Override
    public String isValidInput(Meal meal) {
        if (meal.getPrice() < 1 || meal.getMealType() == null || meal.getName() == null || meal.getName().trim().isEmpty()) {
            return "invalid";
        }
        return "valid";
    }

    @Override
    public String save(Meal meal) {
        try {
           
            Long mealTypeId = meal.getMealType().getId();
            MealType mealType = mealTypeRepository.findById(mealTypeId).orElse(null);
            if (mealType == null) return "invalid";
            meal.setMealType(mealType);

            mealRepository.save(meal);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }

    @Override
    public List<MealDTO> findAll() {
        return mealRepository.findAll().stream()
            .filter(meal -> !meal.isDeleted())
            .map(MealDTO::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<MealDTO> getMealsByMealTypeId(Long mealTypeId) {
        return mealRepository.findAll().stream()
            .filter(meal -> !meal.isDeleted() && meal.getMealType() != null && mealTypeId.equals(meal.getMealType().getId()))
            .map(MealDTO::new)
            .collect(Collectors.toList());
    }

    @Override
    public String delete(Long mealId) {
        try {
            Meal meal = mealRepository.findById(mealId).orElse(null);
            if (meal == null) return "fail";
            meal.setDeleted(true);
            mealRepository.save(meal);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }

    @Override
    public Meal findOne(Long id) {
        return mealRepository.findById(id).orElse(null);
    }

    @Override
    public String editMeal(Meal meal) {
        if (isValidInput(meal).equals("invalid")) {
            return "invalid";
        }
        try {
            Meal existingMeal = mealRepository.findById(meal.getId()).orElse(null);
            if (existingMeal == null) return "fail";

            existingMeal.setPrice(meal.getPrice());
            existingMeal.setName(meal.getName());
            existingMeal.setMealType(meal.getMealType());

            mealRepository.save(existingMeal);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }

    //update
    public String editMeal(Meal meal, MultipartFile image) {
    if (isValidInput(meal).equals("invalid")) {
        return "invalid";
    }
    try {
        Meal existingMeal = mealRepository.findById(meal.getId()).orElse(null);
        if (existingMeal == null) return "fail";

        existingMeal.setPrice(meal.getPrice());
        existingMeal.setName(meal.getName());
        existingMeal.setMealType(meal.getMealType());
        existingMeal.setDescription(meal.getDescription());

        if (image != null && !image.isEmpty()) {
            existingMeal.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            existingMeal.setImageName(image.getOriginalFilename());
        }
        mealRepository.save(existingMeal);
        return "success";
    } catch (Exception e) {
        return "fail";
    }
}
}
