// FoodCategoryDto.java
package com.example.foodService_microservice.foodService.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodCategoryDto {
    private String id;
    private String name;
    private String description;
    private List<FoodItemDto> foodItems;
}
