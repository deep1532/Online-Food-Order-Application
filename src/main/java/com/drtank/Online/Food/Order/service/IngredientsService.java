package com.drtank.Online.Food.Order.service;

import com.drtank.Online.Food.Order.model.Category;
import com.drtank.Online.Food.Order.model.IngredientsCategory;
import com.drtank.Online.Food.Order.model.IngredientsItem;
import com.drtank.Online.Food.Order.model.Restaurant;
import com.drtank.Online.Food.Order.repository.IngredientsCategoryRepository;
import com.drtank.Online.Food.Order.repository.IngredientsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class IngredientsService implements IngredientsServiceInterface{

    @Autowired
    private IngredientsItemRepository ingredientsItemRepository;

    @Autowired
    private IngredientsCategoryRepository ingredientsCategoryRepository;

    @Autowired
    private RestaurantServiceInterface restaurantServiceInterface;


    @Override
    public IngredientsCategory createIngredientsCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant = restaurantServiceInterface.findRestaurantById(restaurantId);

        IngredientsCategory ingredientsCategory = new IngredientsCategory();

        ingredientsCategory.setName(name);
        ingredientsCategory.setRestaurant(restaurant);

        return ingredientsCategoryRepository.save(ingredientsCategory);
    }

    @Override
    public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception {

        Optional<IngredientsCategory> ingredientsCategory = ingredientsCategoryRepository.findById(id);

        if(ingredientsCategory.isEmpty()){
            throw new Exception("Ingredients Category not found...");
        }

        return ingredientsCategory.get();
    }

    @Override
    public List<IngredientsCategory> findIngredientsCategoryByRestaurantsId(Long restaurantId) throws Exception {

        restaurantServiceInterface.findRestaurantById(restaurantId);

        return ingredientsCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {

        Restaurant restaurant = restaurantServiceInterface.findRestaurantById(restaurantId);

        IngredientsCategory ingredientsCategory = findIngredientsCategoryById(categoryId);

        IngredientsItem ingredientsItem = new IngredientsItem();

        ingredientsItem.setName(ingredientName);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setCategory(ingredientsCategory);

        IngredientsItem ingredient = ingredientsItemRepository.save(ingredientsItem);
        ingredientsCategory.getIngredients().add(ingredient);

        return ingredient;
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {
        return ingredientsItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {

        Optional<IngredientsItem> ingredientsItem = ingredientsItemRepository.findById(id);

        if(ingredientsItem.isEmpty()){
            throw new Exception("Ingredient item not found...");
        }

        IngredientsItem ingredient = ingredientsItem.get();
        ingredient.setInStock(!ingredient.isInStock());

        return ingredientsItemRepository.save(ingredient);
    }
}
