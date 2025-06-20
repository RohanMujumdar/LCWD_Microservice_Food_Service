// FoodCategoryService.java
package com.example.foodService_microservice.foodService.service;

import com.example.foodService_microservice.foodService.dto.FoodCategoryDto;

import java.util.List;

public interface FoodCategoryService {
    FoodCategoryDto createCategory(FoodCategoryDto dto);
    FoodCategoryDto getCategoryById(String id);
    FoodCategoryDto updateCategoryById(String id, FoodCategoryDto dto);
    List<FoodCategoryDto> getAllCategories();
    void deleteCategory(String id);
}
