package com.example.foodService_microservice.foodService.controller;

import com.example.foodService_microservice.foodService.dto.FoodCategoryDto;
import com.example.foodService_microservice.foodService.service.FoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("micro/api/food_categories")
public class FoodCategoryController {

    @Autowired
    private FoodCategoryService foodCategoryService;

    // ✅ Create a new food category
    @PostMapping("/")
    public ResponseEntity<FoodCategoryDto> createCategory(@RequestBody FoodCategoryDto dto) {
        dto.setId(UUID.randomUUID().toString());
        FoodCategoryDto created = foodCategoryService.createCategory(dto);
        return ResponseEntity.ok(created);
    }

    // ✅ Get a category by ID
    @GetMapping("/{id}")
    public ResponseEntity<FoodCategoryDto> getCategoryById(@PathVariable String id) {
        FoodCategoryDto dto = foodCategoryService.getCategoryById(id);
        return ResponseEntity.ok(dto);
    }

    // ✅ Get all categories
    @GetMapping("/")
    public ResponseEntity<List<FoodCategoryDto>> getAllCategories() {
        List<FoodCategoryDto> categories = foodCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodCategoryDto> updateCategoryById(@PathVariable String id, @RequestBody FoodCategoryDto foodCategoryDto) {
        FoodCategoryDto dto = foodCategoryService.updateCategoryById(id, foodCategoryDto);
        return ResponseEntity.ok(dto);
    }

    // ✅ Delete a category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        foodCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
