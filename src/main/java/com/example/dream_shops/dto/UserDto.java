package com.example.dream_shops.dto;

import com.example.dream_shops.model.Cart;
import com.example.dream_shops.model.CartItem;
import com.example.dream_shops.model.Order;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
//    private Cart carts;
//    private List<CartItem> cartItems;
//    private List<ProductDto> products;
}
