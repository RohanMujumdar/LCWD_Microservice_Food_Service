package com.example.foodService_microservice.foodService.service;

import com.example.foodService_microservice.foodService.dto.FoodCategoryDto;
import com.example.foodService_microservice.foodService.entity.FoodCategory;
import com.example.foodService_microservice.foodService.repository.FoodCategoryRepository;
import com.example.foodService_microservice.foodService.service.FoodCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FoodCategoryServiceImpl implements FoodCategoryService {

    @Autowired
    private FoodCategoryRepository categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FoodCategoryDto createCategory(FoodCategoryDto dto) {
        FoodCategory category = modelMapper.map(dto, FoodCategory.class);
        category.setId(UUID.randomUUID().toString());
        FoodCategory saved = categoryRepo.save(category);
        return modelMapper.map(saved, FoodCategoryDto.class);
    }

    @Override
    public FoodCategoryDto getCategoryById(String id) {
        FoodCategory category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return modelMapper.map(category, FoodCategoryDto.class);
    }

    @Override
    public FoodCategoryDto updateCategoryById(String id, FoodCategoryDto dto) {
        FoodCategory existingCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        // Update fields (you can customize this based on actual fields in FoodCategory)
        existingCategory.setName(dto.getName());
        existingCategory.setDescription(dto.getDescription());

        FoodCategory updatedCategory = categoryRepo.save(existingCategory);
        return modelMapper.map(updatedCategory, FoodCategoryDto.class);
    }


    @Override
    public List<FoodCategoryDto> getAllCategories() {
        return categoryRepo.findAll()
                .stream()
                .map(category -> modelMapper.map(category, FoodCategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(String id) {
        if (!categoryRepo.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepo.deleteById(id);
    }
}
