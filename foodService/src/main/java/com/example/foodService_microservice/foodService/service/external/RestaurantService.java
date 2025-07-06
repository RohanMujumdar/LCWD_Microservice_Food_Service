package com.example.foodService_microservice.foodService.service.external;

import com.example.foodService_microservice.foodService.config.AppConstants;
import com.example.foodService_microservice.foodService.dto.RestaurantDto;
import com.example.foodService_microservice.foodService.service.external.fallback.RestaurantServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// This is service class is specifically created for feign client.
// We are using Feign Client to fetch the data from Restaurant Service.

// @FeignClient(name = "restaurantService", url = "http://localhost:9091")
@FeignClient(name = AppConstants.RESTAURANT_SERVICE_NAME, fallback = RestaurantServiceFallback.class)
public interface RestaurantService {


    // Feign Client is very declarative, implementation of the below method is
    // automatically taken care off by Feign Client.
    // You need not provide the implementation for it.

    // Get Restaurant By Id
    @GetMapping("/micro/api/restaurants/{id}")
    RestaurantDto getRestaurantById(@PathVariable String id);

    // Get All Restaurants
    @GetMapping("/micro/api/restaurants/")
    List<RestaurantDto> getAllRestaurants();

    // Post
    @PostMapping("/micro/api/restaurants/")
    RestaurantDto createRestaurant(@RequestBody RestaurantDto restaurantDto);

    // Delete
    @DeleteMapping("/micro/api/restaurants/{id}")
    void deleteRestaurant(@PathVariable String id);

    // Put
    @PutMapping("/micro/api/restaurants/{id}")
    RestaurantDto updateRestaurant(@PathVariable String id);
}
