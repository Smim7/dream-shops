package com.example.dream_shops.service.cart;

import com.example.dream_shops.exception.ResourceNotFoundExceotion;
import com.example.dream_shops.model.Cart;
import com.example.dream_shops.model.CartItem;
import com.example.dream_shops.repository.CartItemRepository;
import com.example.dream_shops.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        Cart cart=cartRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundExceotion("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void cleanCart(Long id) {
        Cart cart=getCart(id);
        cartRepository.deleteAllBYCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart=getCart(id);
        return cart.getTotalAmount();

}}
