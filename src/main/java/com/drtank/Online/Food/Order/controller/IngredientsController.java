package com.drtank.Online.Food.Order.controller;

import com.drtank.Online.Food.Order.model.IngredientsCategory;
import com.drtank.Online.Food.Order.model.IngredientsItem;
import com.drtank.Online.Food.Order.request.IngredientsCategoryRequest;
import com.drtank.Online.Food.Order.request.IngredientsItemRequest;
import com.drtank.Online.Food.Order.service.IngredientsServiceInterface;
import org.apache.coyote.Response;
import org.hibernate.Remove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientsController {


    private IngredientsServiceInterface ingredientsServiceInterface;


    @PostMapping("/category")
    public ResponseEntity<IngredientsCategory> createIngredientsCategory(
            @RequestBody IngredientsCategoryRequest req) throws Exception {

        IngredientsCategory ingredientsCategory = ingredientsServiceInterface.createIngredientsCategory(
                req.getName(), req.getRestaurantId());

        return new ResponseEntity<>(ingredientsCategory, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientsItem(
            @RequestBody IngredientsItemRequest req) throws Exception {

        IngredientsItem ingredientsItem = ingredientsServiceInterface.createIngredientsItem(
                req.getRestaurantId(), req.getName(), req.getCategoryId());

        return new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItem> updateIngredientsStock(
            @PathVariable Long id) throws Exception {

        IngredientsItem ingredientsItem = ingredientsServiceInterface.updateStock(id);

        return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredients(
            @PathVariable Long restaurantId) throws Exception {

        List<IngredientsItem> ingredientsItems = ingredientsServiceInterface.findRestaurantsIngredients(restaurantId);

        return new ResponseEntity<>(ingredientsItems, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientsCategory>> getRestaurantIngredientsCategory(
            @PathVariable Long restaurantId) throws Exception {

        List<IngredientsCategory> ingredientsCategories = ingredientsServiceInterface.findIngredientsCategoryByRestaurantsId(restaurantId);

        return new ResponseEntity<>(ingredientsCategories, HttpStatus.OK);
    }
}
