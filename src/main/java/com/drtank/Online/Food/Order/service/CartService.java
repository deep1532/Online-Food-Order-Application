package com.drtank.Online.Food.Order.service;

import com.drtank.Online.Food.Order.model.Cart;
import com.drtank.Online.Food.Order.model.CartItem;
import com.drtank.Online.Food.Order.model.Food;
import com.drtank.Online.Food.Order.model.User;
import com.drtank.Online.Food.Order.repository.CartItemRepository;
import com.drtank.Online.Food.Order.repository.CartRepository;
import com.drtank.Online.Food.Order.request.AddCartItemsRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CartService implements CartServiceInterface{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodServiceInterface foodServiceInterface;



    @Override
    public CartItem addItemsToCart(AddCartItemsRequest req, String jwt) throws Exception {

        User user = userServiceInterface.findUserByJwtToken(jwt);

        Food food = foodServiceInterface.findFoodById(req.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for(CartItem cartItem : cart.getItems()){
            if(cartItem.getFood().equals(food)){
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                return updateCartItemsQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem cartItem = new CartItem();

        cartItem.setCart(cart);
        cartItem.setFood(food);
        cartItem.setQuantity(req.getQuantity());
        cartItem.setIngredients(req.getIngredients());
        cartItem.setTotalPrice(req.getQuantity() * food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(cartItem);

        cart.getItems().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemsQuantity(Long cartItemId, int quantity) throws Exception {

        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

        if(cartItem.isEmpty()){
            throw new Exception("Cart Item is not found...");
        }

        CartItem item = cartItem.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);

        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemsFromCart(Long cartItemId, String jwt) throws Exception {

        User user = userServiceInterface.findUserByJwtToken(jwt);

        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

        if(cartItem.isEmpty()){
            throw new Exception("Cart item not found...");
        }

        CartItem item = cartItem.get();

        cart.getItems().remove(item);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {

        Long total = 0L;

        for(CartItem cartItem : cart.getItems()){
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {

        Optional<Cart> cart = cartRepository.findById(id);

        if(cart.isEmpty()){
            throw new Exception("Cart not found with id : " + id);
        }

        return cart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {

        Cart cart = cartRepository.findByCustomerId(userId);

        // this is for order service
        cart.setTotal(calculateCartTotal(cart));

        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {

        Cart cart = findCartByUserId(userId);
        cart.getItems().clear();

        return cartRepository.save(cart);
    }
}
