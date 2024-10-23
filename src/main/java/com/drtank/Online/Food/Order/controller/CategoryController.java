package com.drtank.Online.Food.Order.controller;

import com.drtank.Online.Food.Order.model.Category;
import com.drtank.Online.Food.Order.model.Restaurant;
import com.drtank.Online.Food.Order.model.User;
import com.drtank.Online.Food.Order.service.CategoryServiceInterface;
import com.drtank.Online.Food.Order.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryServiceInterface categoryServiceInterface;

    @Autowired
    private UserServiceInterface userServiceInterface;


    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userServiceInterface.findUserByJwtToken(jwt);

        Category createdCategory = categoryServiceInterface.createCategory(category.getName(), user.getId());

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(@RequestHeader("Authorization") String jwt) throws Exception{

        User user = userServiceInterface.findUserByJwtToken(jwt);

        List<Category> categories = categoryServiceInterface.findCategoryByRestaurantId(user.getId());

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


}
