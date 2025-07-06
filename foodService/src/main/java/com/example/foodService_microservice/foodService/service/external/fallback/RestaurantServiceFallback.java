package com.example.foodService_microservice.foodService.service.external.fallback;

import com.example.foodService_microservice.foodService.dto.RestaurantDto;
import com.example.foodService_microservice.foodService.service.external.RestaurantService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantServiceFallback implements RestaurantService {
    @Override
    public RestaurantDto getRestaurantById(String id) {
        System.out.println("Fallback executed");
        return null;
    }

    @Override
    public List<RestaurantDto> getAllRestaurants() {
        return List.of();
    }

    @Override
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        return null;
    }

    @Override
    public void deleteRestaurant(String id) {

    }

    @Override
    public RestaurantDto updateRestaurant(String id) {
        return null;
    }
}
