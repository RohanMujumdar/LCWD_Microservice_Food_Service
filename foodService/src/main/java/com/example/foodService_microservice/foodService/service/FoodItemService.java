// FoodItemService.java
package com.example.foodService_microservice.foodService.service;

import com.example.foodService_microservice.foodService.dto.FoodItemDto;

import java.util.List;

public interface FoodItemService {
    FoodItemDto createFoodItem(FoodItemDto dto);
    FoodItemDto getFoodItemById(String id);
    FoodItemDto updateFoodItemById(String id, FoodItemDto dto);
    List<FoodItemDto> getAllFoodItems();
    List<FoodItemDto> getItemsByRestaurant(String restaurantId);
    void deleteFoodItem(String id);
}
