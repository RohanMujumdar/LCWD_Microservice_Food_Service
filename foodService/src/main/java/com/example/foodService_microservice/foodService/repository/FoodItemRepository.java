package com.example.foodService_microservice.foodService.repository;

import com.example.foodService_microservice.foodService.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem, String> {
    List<FoodItem> findByRestaurantId(String restaurantId);
}
