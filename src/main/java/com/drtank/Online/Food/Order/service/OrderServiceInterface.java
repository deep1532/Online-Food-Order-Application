package com.drtank.Online.Food.Order.service;

import com.drtank.Online.Food.Order.model.Order;
import com.drtank.Online.Food.Order.model.User;
import com.drtank.Online.Food.Order.request.CreateOrderRequest;

import java.util.List;

public interface OrderServiceInterface {

    public Order createOrder(CreateOrderRequest order, User user) throws Exception;

    public Order updateOrderStatus(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUserOrders(Long userId) throws Exception;

    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception;

    public Order findOrderById(Long orderId) throws Exception;
}
