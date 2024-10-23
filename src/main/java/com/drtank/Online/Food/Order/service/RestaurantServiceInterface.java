package com.drtank.Online.Food.Order.service;

import com.drtank.Online.Food.Order.dto.RestaurantDto;
import com.drtank.Online.Food.Order.model.Restaurant;
import com.drtank.Online.Food.Order.model.User;
import com.drtank.Online.Food.Order.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantServiceInterface {

    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurants();

    public List<Restaurant> searchRestaurants(String query);

    public Restaurant findRestaurantById(Long restaurantId) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;


}
