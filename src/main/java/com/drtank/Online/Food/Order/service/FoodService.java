package com.drtank.Online.Food.Order.service;

import com.drtank.Online.Food.Order.model.Category;
import com.drtank.Online.Food.Order.model.Food;
import com.drtank.Online.Food.Order.model.Restaurant;
import com.drtank.Online.Food.Order.repository.FoodRepository;
import com.drtank.Online.Food.Order.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodService implements FoodServiceInterface{

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant) {

        Food food = new Food();

        food.setFoodCategory(category);
        food.setImages(request.getImages());
        food.setDescription(request.getDescription());
        food.setName(request.getName());
        food.setRestaurant(restaurant);
        food.setPrice(request.getPrice());
        food.setVegetarian(request.isVegetarian());
        food.setSeasonal(request.isSeasonal());
        food.setIngredients(request.getIngredients());

        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, String foodCategory,
                                         boolean isSeasonal, boolean isVeg, boolean isNonVeg) {

        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        if(isVeg){
            foods = filterByVeg(foods, isVeg);
        }

        if(isNonVeg){
            foods = filterByNonVeg(foods, isNonVeg);
        }

        if(isSeasonal){
            foods = filterBySeasonal(foods, isSeasonal);
        }

        if(foodCategory != null && !foodCategory.isEmpty()){
            foods = filterByCategory(foods, foodCategory);
        }

        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if(food.getFoodCategory() != null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food -> !food.isVegetarian()).collect(Collectors.toList());
    }

    private List<Food> filterByVeg(List<Food> foods, boolean isVeg) {
        return foods.stream().filter(food -> food.isVegetarian() == isVeg).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String query) {

        return foodRepository.searchFood(query);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> food = foodRepository.findById(foodId);

        if(food.isEmpty()){
            throw new Exception("Food is not exist...");
        }
        return food.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());

        return foodRepository.save(food);
    }
}