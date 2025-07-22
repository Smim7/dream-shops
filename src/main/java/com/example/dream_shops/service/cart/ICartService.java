package com.example.dream_shops.service.cart;

import com.example.dream_shops.model.Cart;
import com.example.dream_shops.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void cleanCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
