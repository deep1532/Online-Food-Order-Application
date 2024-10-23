package com.drtank.Online.Food.Order.request;

import com.drtank.Online.Food.Order.model.Category;
import com.drtank.Online.Food.Order.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;
    private Long restaurantId;
    private Category category;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientsItem> ingredients;
    private List<String> images;
}
