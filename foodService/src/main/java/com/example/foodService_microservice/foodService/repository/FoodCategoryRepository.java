package com.example.foodService_microservice.foodService.repository;

import com.example.foodService_microservice.foodService.entity.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, String> {

}
