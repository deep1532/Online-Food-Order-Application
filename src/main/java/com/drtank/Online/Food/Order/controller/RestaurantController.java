package com.drtank.Online.Food.Order.controller;

import com.drtank.Online.Food.Order.dto.RestaurantDto;
import com.drtank.Online.Food.Order.model.Restaurant;
import com.drtank.Online.Food.Order.model.User;
import com.drtank.Online.Food.Order.service.RestaurantServiceInterface;
import com.drtank.Online.Food.Order.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantServiceInterface restaurantServiceInterface;

    @Autowired
    private UserServiceInterface userServiceInterface;


    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String query) throws Exception {

        User user = userServiceInterface.findUserByJwtToken(jwt);

        List<Restaurant> restaurants = restaurantServiceInterface.searchRestaurants(query);

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurants(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userServiceInterface.findUserByJwtToken(jwt);

        List<Restaurant> restaurants = restaurantServiceInterface.getAllRestaurants();

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {

        User user = userServiceInterface.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantServiceInterface.findRestaurantById(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favourites")
    public ResponseEntity<RestaurantDto> addToFavourites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {

        User user = userServiceInterface.findUserByJwtToken(jwt);

        RestaurantDto restaurantDto = restaurantServiceInterface.addToFavourites(id, user);

        return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
    }

}
