package com.drtank.Online.Food.Order.request;

import com.drtank.Online.Food.Order.model.Address;
import lombok.Data;

@Data
public class CreateOrderRequest {

    private Long restaurantId;
    private Address deliveryAddress;
}
