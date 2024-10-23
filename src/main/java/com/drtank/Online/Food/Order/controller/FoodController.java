package com.drtank.Online.Food.Order.controller;

import com.drtank.Online.Food.Order.model.Food;
import com.drtank.Online.Food.Order.model.User;
import com.drtank.Online.Food.Order.service.FoodServiceInterface;
import com.drtank.Online.Food.Order.service.RestaurantServiceInterface;
import com.drtank.Online.Food.Order.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodServiceInterface foodServiceInterface;

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private RestaurantServiceInterface restaurantServiceInterface;


    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String query,
                                                 @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userServiceInterface.findUserByJwtToken(jwt);

        List<Food> foods = foodServiceInterface.searchFood(query);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @PathVariable Long restaurantId, @RequestParam boolean isVeg, @RequestParam boolean isNonVeg,
            @RequestParam boolean isSeasonal, @RequestParam (required = false) String foodCategory,
            @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userServiceInterface.findUserByJwtToken(jwt);

        List<Food> foods = foodServiceInterface.getRestaurantsFood(restaurantId, foodCategory, isSeasonal, isVeg, isNonVeg);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
