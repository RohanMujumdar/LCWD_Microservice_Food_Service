package com.example.foodService_microservice.foodService.service.external;

import com.example.foodService_microservice.foodService.config.AppConstants;
import com.example.foodService_microservice.foodService.dto.RestaurantDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RestWebClientService {

    @Autowired
    private WebClient.Builder webClient;

    public RestaurantDto getRestaurantById(String id)
    {
        RestaurantDto restaurantDto = webClient.baseUrl(AppConstants.RESTAURANT_SERVICE_URL)
                .build()
                .get()
                .uri("/micro/api/restaurants/{id}", id)
                .retrieve()// Data will be Response Spec Format. Hence we need bodyToMono.
                .bodyToMono(RestaurantDto.class)
                .block(); // Makes this operation Blocking.

        return restaurantDto;

    }

    public List<RestaurantDto> getAllRestaurants()
    {
        return webClient.baseUrl(AppConstants.RESTAURANT_SERVICE_URL)
                .build()
                .get()
                .uri("/micro/api/restaurants/")
                .retrieve()
                .bodyToFlux(RestaurantDto.class)
                .collectList()
                .block();
    }

    public RestaurantDto createRestaurant(RestaurantDto restaurantDto)
    {
        return webClient.baseUrl(AppConstants.RESTAURANT_SERVICE_URL)
                .build()
                .post()
                .uri("/micro/api/restaurants/")
                .bodyValue(restaurantDto)
                .header("Authorization", "Bearer abcdefg")
                .retrieve()
                .bodyToMono(RestaurantDto.class)
                .block();
    }

    public RestaurantDto updateRestaurant(RestaurantDto restaurantDto, String id)
    {
        return webClient.baseUrl(AppConstants.RESTAURANT_SERVICE_URL)
                .build()
                .put()
                .uri("/micro/api/restaurants/{id}", id)
                .bodyValue(restaurantDto)
                .retrieve()
                .bodyToMono(RestaurantDto.class)
                .block();

    }

    public void deleteRestaurant(String id)
    {
        webClient.baseUrl(AppConstants.RESTAURANT_SERVICE_URL)
                .build()
                .delete()
                .uri("/micro/api/restaurants/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    // Non-Blocking API, getRestaurantById
    public Mono<RestaurantDto> getRestaurantByIdNonBlocking(String id)
    {
        return webClient.baseUrl(AppConstants.RESTAURANT_SERVICE_URL)
                .build()
                .get()
                .uri("/micro/api/restaurants/{id}", id)
                .retrieve()// Data will be Response Spec Format. Hence we need bodyToMono.
                .bodyToMono(RestaurantDto.class);
    }

    // Non-Blocking API, getAllRestaurants
    public Flux<RestaurantDto> getAllRestaurantsNonBlocking()
    {
        return webClient.baseUrl(AppConstants.RESTAURANT_SERVICE_URL)
                .build()
                .get()
                .uri("/micro/api/restaurants/")
                .retrieve()
                .bodyToFlux(RestaurantDto.class);
    }

}
