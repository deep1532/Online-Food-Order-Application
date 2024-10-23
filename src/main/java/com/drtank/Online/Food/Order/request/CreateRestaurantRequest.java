package com.drtank.Online.Food.Order.request;

import com.drtank.Online.Food.Order.model.Address;
import com.drtank.Online.Food.Order.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {

    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;
}
