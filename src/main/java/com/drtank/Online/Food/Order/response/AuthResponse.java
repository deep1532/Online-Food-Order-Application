package com.drtank.Online.Food.Order.response;

import com.drtank.Online.Food.Order.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;

    private String message;

    private USER_ROLE role;
}
