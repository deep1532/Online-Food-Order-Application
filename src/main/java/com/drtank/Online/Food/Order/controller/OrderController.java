package com.drtank.Online.Food.Order.controller;

import com.drtank.Online.Food.Order.model.Order;
import com.drtank.Online.Food.Order.model.User;
import com.drtank.Online.Food.Order.request.CreateOrderRequest;
import com.drtank.Online.Food.Order.service.OrderServiceInterface;
import com.drtank.Online.Food.Order.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderServiceInterface orderServiceInterface;

    @Autowired
    private UserServiceInterface userServiceInterface;


    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest req,
                                             @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userServiceInterface.findUserByJwtToken(jwt);

        Order order = orderServiceInterface.createOrder(req, user);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistoryForUser(@RequestHeader("Authorization") String jwt) throws Exception{

        User user = userServiceInterface.findUserByJwtToken(jwt);

        List<Order> orders = orderServiceInterface.getUserOrders(user.getId());

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
