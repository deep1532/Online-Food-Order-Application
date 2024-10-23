package com.drtank.Online.Food.Order.controller;

import com.drtank.Online.Food.Order.model.Restaurant;
import com.drtank.Online.Food.Order.model.User;
import com.drtank.Online.Food.Order.request.CreateRestaurantRequest;
import com.drtank.Online.Food.Order.response.MessageResponse;
import com.drtank.Online.Food.Order.service.RestaurantServiceInterface;
import com.drtank.Online.Food.Order.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantServiceInterface restaurantServiceInterface;

    @Autowired
    private UserServiceInterface userServiceInterface;


    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest  req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userServiceInterface.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantServiceInterface.createRestaurant(req, user);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest  req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {

        User user = userServiceInterface.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantServiceInterface.updateRestaurant(id, req);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {

        User user = userServiceInterface.findUserByJwtToken(jwt);

        restaurantServiceInterface.deleteRestaurant(id);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Restaurant deleted successfully!");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {

        User user = userServiceInterface.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantServiceInterface.updateRestaurantStatus(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userServiceInterface.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantServiceInterface.getRestaurantByUserId(user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
