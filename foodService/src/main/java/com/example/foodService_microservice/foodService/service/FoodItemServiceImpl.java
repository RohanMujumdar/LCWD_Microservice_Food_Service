package com.example.foodService_microservice.foodService.service;

import com.example.foodService_microservice.foodService.dto.FoodCategoryDto;
import com.example.foodService_microservice.foodService.dto.FoodItemDto;
import com.example.foodService_microservice.foodService.dto.RestaurantDto;
import com.example.foodService_microservice.foodService.entity.FoodCategory;
import com.example.foodService_microservice.foodService.entity.FoodItem;
import com.example.foodService_microservice.foodService.repository.FoodCategoryRepository;
import com.example.foodService_microservice.foodService.repository.FoodItemRepository;
import com.example.foodService_microservice.foodService.service.external.RestWebClientService;
import com.example.foodService_microservice.foodService.service.external.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FoodItemServiceImpl implements FoodItemService {

    @Autowired
    private FoodItemRepository foodItemRepo;

    @Autowired
    private FoodCategoryRepository categoryRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestWebClientService restWebClientService;

    @Autowired
    private WebClient.Builder webClient;


    @Override
    public FoodItemDto createFoodItem(FoodItemDto dto) {
        FoodItem foodItem = convertDtoToEntity(dto);
        foodItem.setId(UUID.randomUUID().toString());

        // Fetch category from DB to ensure it exists
        FoodCategory category = categoryRepo.findById(dto.getFoodCategoryId())
                .orElseThrow(() -> new RuntimeException("Food category not found"));
        foodItem.setFoodCategory(category);

        FoodItem savedItem = foodItemRepo.save(foodItem);
        return convertEntityToDto(savedItem);
    }

    @Override
    public FoodItemDto getFoodItemById(String id) {
        FoodItem item = foodItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));

        // We will call Restaurant Service to get the restaurant's data. We use Rest Template.
        // We should know the URL of Restaurant service to make the call.

        // RestTemplate
        // String restaurantServiceUrl="http://localhost:9091/micro/api/restaurants/" + item.getRestaurantId();
        // RestaurantDto restaurantDto = restTemplate.getForObject(restaurantServiceUrl, RestaurantDto.class);

        // Feign Client
        // Calling Restaurant Service to get Restaurant by Id.
         RestaurantDto restaurantDto = restaurantService.getRestaurantById(item.getRestaurantId());

        // Web Client
//        RestaurantDto restaurantDto = webClient
//                .baseUrl("http://localhost:9091")
//                .build()
//                .get()
//                .uri("/micro/api/restaurants/{id}", item.getRestaurantId())
//                .retrieve()// Data will be Response Spec Format. Hence we need bodyToMono.
//                .bodyToMono(RestaurantDto.class)
//                .block(); // Makes this operation Blocking.

//        RestaurantDto restaurantDto = restWebClientService.getRestaurantById(item.getRestaurantId());

        FoodItemDto foodItemDto = convertEntityToDto(item);
        foodItemDto.setRestaurantDto(restaurantDto);

        return foodItemDto;
    }

    @Override
    public FoodItemDto updateFoodItemById(String id, FoodItemDto dto) {
        FoodItem existingItem = foodItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found with id: " + id));

        // Update basic fields
        existingItem.setTitle(dto.getTitle());
        existingItem.setDescription(dto.getDescription());
        existingItem.setQuantity(dto.getQuantity());
        existingItem.setOutOfStock(dto.isOutOfStock());
        existingItem.setFoodType(dto.getFoodType());
        existingItem.setRestaurantId(dto.getRestaurantId());

        // Update category if provided
        if (dto.getFoodCategoryId() != null) {
            FoodCategory category = categoryRepo.findById(dto.getFoodCategoryId())
                    .orElseThrow(() -> new RuntimeException("Food category not found with id: " + dto.getFoodCategoryId()));
            existingItem.setFoodCategory(category);
        }

        FoodItem updatedItem = foodItemRepo.save(existingItem);

        return convertEntityToDto(updatedItem);
    }


    @Override
    public List<FoodItemDto> getAllFoodItems() {
        return foodItemRepo.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FoodItemDto> getItemsByRestaurant(String restaurantId) {
        return foodItemRepo.findByRestaurantId(restaurantId)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFoodItem(String id) {
        if (!foodItemRepo.existsById(id)) {
            throw new RuntimeException("Food item not found");
        }
        foodItemRepo.deleteById(id);
    }

    private FoodItemDto convertEntityToDto(FoodItem foodItem) {
        FoodItemDto foodItemDto = new FoodItemDto();

        foodItemDto.setId(foodItem.getId());
        foodItemDto.setTitle(foodItem.getTitle());
        foodItemDto.setDescription(foodItem.getDescription());
        foodItemDto.setQuantity(foodItem.getQuantity());
        foodItemDto.setOutOfStock(foodItem.isOutOfStock());
        foodItemDto.setFoodType(foodItem.getFoodType());

        if (foodItem.getFoodCategory() != null) {
            foodItemDto.setFoodCategoryId(foodItem.getFoodCategory().getId());

            FoodCategory foodCategory = foodItem.getFoodCategory();
            FoodCategoryDto foodCategoryDto = new FoodCategoryDto();

            foodCategoryDto.setId(foodCategory.getId());
            foodCategoryDto.setName(foodCategory.getName());
            foodCategoryDto.setDescription(foodCategory.getDescription());

            foodItemDto.setFoodCategoryDto(foodCategoryDto);
        }

        foodItemDto.setRestaurantId(foodItem.getRestaurantId());
        return foodItemDto;
    }

    private FoodItem convertDtoToEntity(FoodItemDto foodItemDto) {
        FoodItem foodItem = new FoodItem();

        foodItem.setId(foodItemDto.getId());
        foodItem.setTitle(foodItemDto.getTitle());
        foodItem.setDescription(foodItemDto.getDescription());
        foodItem.setQuantity(foodItemDto.getQuantity());
        foodItem.setOutOfStock(foodItemDto.isOutOfStock());
        foodItem.setFoodType(foodItemDto.getFoodType());
        foodItem.setRestaurantId(foodItemDto.getRestaurantId());

        if (foodItemDto.getFoodCategoryDto() != null) {
            FoodCategoryDto foodCategoryDto = foodItemDto.getFoodCategoryDto();

            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setId(foodCategoryDto.getId());
            foodCategory.setName(foodCategoryDto.getName());
            foodCategory.setDescription(foodCategoryDto.getDescription());

            foodItem.setFoodCategory(foodCategory);
        } else if (foodItemDto.getFoodCategoryId() != null) {
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setId(foodItemDto.getFoodCategoryId());
            foodItem.setFoodCategory(foodCategory);
        }

        return foodItem;
    }
}
