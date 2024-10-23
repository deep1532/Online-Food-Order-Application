package com.drtank.Online.Food.Order.controller;

import com.drtank.Online.Food.Order.model.Food;
import com.drtank.Online.Food.Order.model.Restaurant;
import com.drtank.Online.Food.Order.model.User;
import com.drtank.Online.Food.Order.request.CreateFoodRequest;
import com.drtank.Online.Food.Order.response.MessageResponse;
import com.drtank.Online.Food.Order.service.FoodServiceInterface;
import com.drtank.Online.Food.Order.service.RestaurantServiceInterface;
import com.drtank.Online.Food.Order.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodServiceInterface foodServiceInterface;

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private RestaurantServiceInterface restaurantServiceInterface;


    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userServiceInterface.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantServiceInterface.findRestaurantById(req.getRestaurantId());

        Food food = foodServiceInterface.createFood(req, req.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userServiceInterface.findUserByJwtToken(jwt);

        foodServiceInterface.deleteFood(id);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Food is deleted successfully!");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userServiceInterface.findUserByJwtToken(jwt);

        Food food = foodServiceInterface.updateAvailabilityStatus(id);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
