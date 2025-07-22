package com.example.dream_shops.controller;

import com.example.dream_shops.dto.OrderDto;
import com.example.dream_shops.exception.ResourceNotFoundExceotion;
import com.example.dream_shops.model.Order;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.order.OrderService;
import com.example.dream_shops.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

@PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            Order order=orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Item order successfully created", order));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error Occurred", e.getMessage()));
        }
    }
@GetMapping("/{orderId}/order")
     public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
       try {
           OrderDto order=orderService.getOrder(orderId);
           return ResponseEntity.ok(new ApiResponse("Item order successful", order));
       } catch (ResourceNotFoundExceotion e) {
          return ResponseEntity.status(NOT_FOUND)
                  .body(new ApiResponse("Oops!", e.getMessage()));
       }
   }
    @GetMapping("/{UserId}/order")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long UserId) {
        try {
            List<OrderDto> order =orderService.getUserOrders(UserId);
            return ResponseEntity.ok(new ApiResponse("Item order successful", order));
        } catch (ResourceNotFoundExceotion e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Oops!", e.getMessage()));
        }
    }
}
