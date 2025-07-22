package com.example.foodorderback.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.foodorderback.dto.MealTypeDTO;
import com.example.foodorderback.model.MealType;
import com.example.foodorderback.repository.MealTypeRepository;
import com.example.foodorderback.service.MealTypeService;
import com.example.foodorderback.model.Meal;
import com.example.foodorderback.repository.MealRepository;

@Service
public class MealTypeServiceImpl implements MealTypeService {

    @Autowired
    private MealTypeRepository mealTypeRepository;

    @Autowired
    private MealRepository mealRepository;

    @Override
    public List<MealTypeDTO> getAllMealTypes() {
        return mealTypeRepository.findAll().stream()
                .filter(mealType -> !mealType.isDeleted())
                .map(MealTypeDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public String isValidInput(MealType mealType) {
        if (mealType.getTypeName() == null || mealType.getTypeName().trim().isEmpty() ||
            mealType.getDescription() == null || mealType.getDescription().trim().isEmpty()) {
            return "invalid";
        }
        return "valid";
    }

    @Override
    public String save(MealType mealType) {
        try {
            mealTypeRepository.save(mealType);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }

    @Override
    public String delete(Long mealTypeId) {
        try {
            Optional<MealType> optionalMealType = mealTypeRepository.findById(mealTypeId);
            if (optionalMealType.isPresent()) {
                MealType mealType = optionalMealType.get();
                mealType.setDeleted(true);
                mealTypeRepository.save(mealType);
                return "success";
            }
        } catch (Exception e) {
            // Log error if needed
        }
        return "fail";
    }

    @Override
    public MealType findOne(Long id) {
        return mealTypeRepository.findById(id).orElse(null);
    }

    @Override
    public String editMealType(MealType mealType) {
        if ("invalid".equals(isValidInput(mealType))) {
            return "invalid";
        }
        try {
            Optional<MealType> optionalMealType = mealTypeRepository.findById(mealType.getId());
            if (optionalMealType.isPresent()) {
                MealType mt = optionalMealType.get();
                mt.setTypeName(mealType.getTypeName());
                mt.setDescription(mealType.getDescription());
                mealTypeRepository.save(mt);
                return "success";
            }
        } catch (Exception e) {
            // Log error if needed
        }
        return "fail";
    }

    @Override
    public String editMealTypeWithImage(MealType mealType) {
        if ("invalid".equals(isValidInput(mealType))) {
            return "invalid";
        }
        try {
            Optional<MealType> optionalMealType = mealTypeRepository.findById(mealType.getId());
            if (optionalMealType.isPresent()) {
                MealType mt = optionalMealType.get();
                mt.setTypeName(mealType.getTypeName());
                mt.setDescription(mealType.getDescription());
                mt.setImage(mealType.getImage());
                mt.setImageName(mealType.getImageName());
                mealTypeRepository.save(mt);
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return "fail";
    }
}
