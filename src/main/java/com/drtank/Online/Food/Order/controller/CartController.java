package com.drtank.Online.Food.Order.controller;

import com.drtank.Online.Food.Order.model.Cart;
import com.drtank.Online.Food.Order.model.CartItem;
import com.drtank.Online.Food.Order.model.User;
import com.drtank.Online.Food.Order.request.AddCartItemsRequest;
import com.drtank.Online.Food.Order.request.UpdateCartItemRequest;
import com.drtank.Online.Food.Order.service.CartServiceInterface;
import com.drtank.Online.Food.Order.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    private CartServiceInterface cartServiceInterface;

    private UserServiceInterface userServiceInterface;


    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemsRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws  Exception{

        CartItem cartItem = cartServiceInterface.addItemsToCart(req, jwt);

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws  Exception{

        CartItem cartItem = cartServiceInterface.updateCartItemsQuantity(req.getCartItemId(), req.getQuantity());

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long id,
                                                  @RequestHeader("Authorization") String jwt) throws  Exception{

        Cart cart = cartServiceInterface.removeItemsFromCart(id, jwt);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws  Exception{

        User user = userServiceInterface.findUserByJwtToken(jwt);

        Cart cart = cartServiceInterface.clearCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws  Exception{

        User user = userServiceInterface.findUserByJwtToken(jwt);

        Cart cart = cartServiceInterface.findCartByUserId(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
