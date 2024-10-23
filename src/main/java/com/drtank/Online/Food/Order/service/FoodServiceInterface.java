package com.drtank.Online.Food.Order.service;

import com.drtank.Online.Food.Order.model.Category;
import com.drtank.Online.Food.Order.model.Food;
import com.drtank.Online.Food.Order.model.Restaurant;
import com.drtank.Online.Food.Order.request.CreateFoodRequest;

import java.util.List;

public interface FoodServiceInterface {

    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);

    public void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId, String foodCategory, boolean isSeasonal,
                                         boolean isVeg, boolean isNonVeg);

    public List<Food> searchFood(String query);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailabilityStatus(Long foodId) throws Exception;
}
