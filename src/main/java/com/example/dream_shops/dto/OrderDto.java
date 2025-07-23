package com.example.dream_shops.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
@Data
public class OrderDto {
    private Long id;
    private Long UserId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String status;
    private Set<OrderItemDto> orderItems;
}