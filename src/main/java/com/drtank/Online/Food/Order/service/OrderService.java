package com.drtank.Online.Food.Order.service;

import com.drtank.Online.Food.Order.model.*;
import com.drtank.Online.Food.Order.repository.AddressRepository;
import com.drtank.Online.Food.Order.repository.OrderItemRepository;
import com.drtank.Online.Food.Order.repository.OrderRepository;
import com.drtank.Online.Food.Order.repository.UserRepository;
import com.drtank.Online.Food.Order.request.CreateOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements OrderServiceInterface{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    private RestaurantServiceInterface restaurantServiceInterface;

    private CartServiceInterface cartServiceInterface;


    @Override
    public Order createOrder(CreateOrderRequest order, User user) throws Exception {

        Address shippedAddress = order.getDeliveryAddress();

        Address savesAddress = addressRepository.save(shippedAddress);

        if(!user.getAddresses().contains(savesAddress)){
            user.getAddresses().add(savesAddress);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantServiceInterface.findRestaurantById(order.getRestaurantId());

        Cart cart = cartServiceInterface.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItem cartItem : cart.getItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);

            orderItems.add(savedOrderItem);
        }

        Order createdOrder = new Order();

        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setCustomer(user);
        createdOrder.setDeliveryAddress(savesAddress);
        createdOrder.setRestaurant(restaurant);
        createdOrder.setItems(orderItems);

        Long totalPrice = cartServiceInterface.calculateCartTotal(cart);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);

        return savedOrder;
    }

    @Override
    public Order updateOrderStatus(Long orderId, String orderStatus) throws Exception {

        Order order = findOrderById(orderId);

        if(orderStatus.equals("PENDING") || orderStatus.equals("COMPLETED") ||
                orderStatus.equals("DELIVERED") || orderStatus.equals("OUT_FOR_DELIVERY")){

            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }

        throw new Exception("Please Select a valid Order Status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {

        Order order = findOrderById(orderId);

        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUserOrders(Long userId) throws Exception {

        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);

        if(orderStatus != null){
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }

        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {

        Optional<Order> order = orderRepository.findById(orderId);

        if(order.isEmpty()){
            throw new Exception("Order is not found with orderId : " + orderId);
        }

        return order.get();
    }
}
