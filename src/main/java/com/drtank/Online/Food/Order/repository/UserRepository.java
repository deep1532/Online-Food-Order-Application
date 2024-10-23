package com.drtank.Online.Food.Order.repository;

import com.drtank.Online.Food.Order.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByEmail(String username);
}
