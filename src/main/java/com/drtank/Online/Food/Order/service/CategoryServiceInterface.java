package com.drtank.Online.Food.Order.service;

import com.drtank.Online.Food.Order.model.Category;

import java.util.List;

public interface CategoryServiceInterface {

    public Category createCategory(String name, Long userId) throws Exception;

    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception;

    public Category findCategoryById(Long id) throws Exception;
}
