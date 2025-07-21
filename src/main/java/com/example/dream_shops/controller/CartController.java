package com.example.dream_shops.controller;

import com.example.dream_shops.exception.ResourceNotFoundExceotion;
import com.example.dream_shops.model.Cart;
import com.example.dream_shops.repository.CartRepository;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;
    private final CartRepository cartRepository;

@GetMapping("{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success", cart));
        } catch (ResourceNotFoundExceotion e) {
           return ResponseEntity.status(NOT_FOUND)
                   .body(new ApiResponse(e.getMessage(), null));
        }
    }

@DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
    try {
        cartService.cleanCart(cartId);
        return ResponseEntity.ok(new ApiResponse(" Clear Cart Success",null));
    } catch (ResourceNotFoundExceotion e) {
        return ResponseEntity.status(NOT_FOUND)
                .body(new ApiResponse(e.getMessage(), null));
    }
}

@GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) {
    try {
        BigDecimal totalPrice = cartService.getTotalPrice(cartId);
        return ResponseEntity.ok(new ApiResponse("Total price", totalPrice));
    } catch (ResourceNotFoundExceotion e) {
        return ResponseEntity.status(NOT_FOUND)
                .body(new ApiResponse(e.getMessage(), null));
    }
}
}
