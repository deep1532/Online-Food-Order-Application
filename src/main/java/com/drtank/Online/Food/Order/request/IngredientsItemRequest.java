package com.drtank.Online.Food.Order.request;

import lombok.Data;

@Data
public class IngredientsItemRequest {

    private String name;
    private Long restaurantId;
    private Long categoryId;
}
