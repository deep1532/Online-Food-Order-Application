package com.drtank.Online.Food.Order.service;

import com.drtank.Online.Food.Order.model.User;

public interface UserServiceInterface {

    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;
}
