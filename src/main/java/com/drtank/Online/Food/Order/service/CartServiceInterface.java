package com.drtank.Online.Food.Order.service;

import com.drtank.Online.Food.Order.model.Cart;
import com.drtank.Online.Food.Order.model.CartItem;
import com.drtank.Online.Food.Order.request.AddCartItemsRequest;

public interface CartServiceInterface {

    public CartItem addItemsToCart(AddCartItemsRequest req, String jwt) throws Exception;

    public CartItem updateCartItemsQuantity(Long cartItemId, int quantity) throws Exception;

    public Cart removeItemsFromCart(Long cartItemId, String jwt) throws Exception;

    public Long calculateCartTotal(Cart cart) throws Exception;

    public Cart findCartById(Long id) throws Exception;

    public Cart findCartByUserId(Long userId) throws Exception;

    public Cart clearCart(Long userId) throws Exception;

}
