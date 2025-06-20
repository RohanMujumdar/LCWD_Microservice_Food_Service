// FoodItemDto.java
package com.example.foodService_microservice.foodService.dto;

import com.example.foodService_microservice.foodService.entity.FoodType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemDto {
    private String id;
    private String title;
    private String description;
    private int quantity;
    private boolean outOfStock;
    private FoodType foodType;
    private String restaurantId;
    private String foodCategoryId;
    private FoodCategoryDto foodCategoryDto;
    private RestaurantDto restaurantDto;
}
