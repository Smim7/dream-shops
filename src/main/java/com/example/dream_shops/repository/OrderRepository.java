package com.example.dream_shops.repository;

import com.example.dream_shops.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderItem,Long> {
}
