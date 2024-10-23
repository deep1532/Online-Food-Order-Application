package com.drtank.Online.Food.Order.repository;

import com.drtank.Online.Food.Order.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
