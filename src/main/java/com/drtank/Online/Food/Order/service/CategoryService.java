package com.drtank.Online.Food.Order.service;

import com.drtank.Online.Food.Order.model.Category;
import com.drtank.Online.Food.Order.model.Restaurant;
import com.drtank.Online.Food.Order.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryServiceInterface {

    @Autowired
    private RestaurantServiceInterface restaurantServiceInterface;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Category createCategory(String name, Long userId) throws Exception {

        Restaurant restaurant = restaurantServiceInterface.getRestaurantByUserId(userId);

        Category category = new Category();

        category.setName(name);;
        category.setRestaurant(restaurant);

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long userId) throws Exception {

        Restaurant restaurant = restaurantServiceInterface.getRestaurantByUserId(userId);

        return categoryRepository.findByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {

        Optional<Category> category = categoryRepository.findById(id);

        if(category.isEmpty()){
            throw new Exception("Category not found...");
        }

        return category.get();
    }
}
