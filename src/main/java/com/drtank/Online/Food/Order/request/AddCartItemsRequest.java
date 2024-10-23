package com.drtank.Online.Food.Order.request;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemsRequest {

    private Long foodId;
    private int quantity;
    private List<String> ingredients;
}
