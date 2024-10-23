package com.drtank.Online.Food.Order.service;

import com.drtank.Online.Food.Order.model.IngredientsCategory;
import com.drtank.Online.Food.Order.model.IngredientsItem;

import java.util.List;

public interface IngredientsServiceInterface {

    public IngredientsCategory createIngredientsCategory(String name, Long restaurantId) throws Exception;

    public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception;

    public List<IngredientsCategory> findIngredientsCategoryByRestaurantsId(Long restaurantId) throws Exception;

    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;

    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId);

    public IngredientsItem updateStock(Long id) throws Exception;
}
