package com.example.dream_shops.service.cart;

import com.example.dream_shops.model.CartItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


public interface ICartItemService{
    void addItemToCart(Long cartId,Long ProductId,int quantity);
    void removeItemFromCart(Long cartId,Long ProductId);
   void updateItemQuantity(Long cartId,Long ProductId,int quantity);

    CartItem getCartItem(Long cartId, Long ProductId);
}
