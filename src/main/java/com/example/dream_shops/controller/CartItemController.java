package com.example.dream_shops.controller;

import com.example.dream_shops.exception.ResourceNotFoundExceotion;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.cart.CartItemService;
import com.example.dream_shops.service.cart.ICartItemService;
import com.example.dream_shops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;

@PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addCartItem(@RequestParam (required = false)Long cartId,
                                                   @RequestParam Long ProductId,
                                                   @RequestParam Integer quantity) {
        try {
            if(cartId==null){
              cartId=  cartService.initializeNewCart();
            }
            cartItemService.addItemToCart(cartId, ProductId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add Item Success",null));
        } catch (ResourceNotFoundExceotion e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

@DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeCartItem(@PathVariable Long cartId,
                                                      @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("Remove Item Success",null));
        }
        catch (ResourceNotFoundExceotion e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

@PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long itemId,
                                                         @RequestParam int quantity) {

    try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse("Update Item Quantity Success",null));
    }
    catch (ResourceNotFoundExceotion e) {
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
    }
}
