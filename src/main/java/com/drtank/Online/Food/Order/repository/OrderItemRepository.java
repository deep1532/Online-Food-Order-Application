package com.drtank.Online.Food.Order.repository;

import com.drtank.Online.Food.Order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
