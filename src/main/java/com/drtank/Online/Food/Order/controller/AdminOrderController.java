package com.drtank.Online.Food.Order.controller;

import com.drtank.Online.Food.Order.model.Order;
import com.drtank.Online.Food.Order.model.User;
import com.drtank.Online.Food.Order.service.OrderServiceInterface;
import com.drtank.Online.Food.Order.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderServiceInterface orderServiceInterface;

    @Autowired
    private UserServiceInterface userServiceInterface;


    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistoryForRestaurant(@PathVariable Long restaurantId,
                                                                    @RequestParam(required = false) String orderStatus,
                                                                    @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userServiceInterface.findUserByJwtToken(jwt);

        List<Order> orders = orderServiceInterface.getRestaurantOrders(restaurantId, orderStatus);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/order/{id}/{status}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,
                                                                    @PathVariable String orderStatus,
                                                                    @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userServiceInterface.findUserByJwtToken(jwt);

        Order order = orderServiceInterface.updateOrderStatus(orderId, orderStatus);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
