package com.example.dream_shops.service.order;

import com.example.dream_shops.dto.OrderDto;
import com.example.dream_shops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder (Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
