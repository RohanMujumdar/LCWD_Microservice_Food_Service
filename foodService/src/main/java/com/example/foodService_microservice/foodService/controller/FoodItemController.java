
package com.example.foodService_microservice.foodService.controller;

import com.example.foodService_microservice.foodService.dto.FoodItemDto;
import com.example.foodService_microservice.foodService.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("micro/api/food_items")
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    // ✅ Create a new food item
    @PostMapping("/")
    public ResponseEntity<FoodItemDto> createFoodItem(@RequestBody FoodItemDto dto) {
        FoodItemDto created = foodItemService.createFoodItem(dto);
        return ResponseEntity.ok(created);
    }

    // ✅ Get a single food item by ID
    @GetMapping("/{id}")
    public ResponseEntity<FoodItemDto> getFoodItemById(@PathVariable String id) {
        FoodItemDto item = foodItemService.getFoodItemById(id);
        return ResponseEntity.ok(item);
    }

    // ✅ Get all food items
    @GetMapping("/")
    public ResponseEntity<List<FoodItemDto>> getAllFoodItems() {
        List<FoodItemDto> items = foodItemService.getAllFoodItems();
        return ResponseEntity.ok(items);
    }

    // ✅ Get all food items for a specific restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodItemDto>> getItemsByRestaurant(@PathVariable String restaurantId) {
        List<FoodItemDto> items = foodItemService.getItemsByRestaurant(restaurantId);
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItemDto> updateItemsByRestaurant(@PathVariable String id, @RequestBody  FoodItemDto foodItemDto) {
        FoodItemDto items = foodItemService.updateFoodItemById(id, foodItemDto);
        return ResponseEntity.ok(items);
    }

    // ✅ Delete a food item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable String id) {
        foodItemService.deleteFoodItem(id);
        return ResponseEntity.noContent().build();
    }
}
