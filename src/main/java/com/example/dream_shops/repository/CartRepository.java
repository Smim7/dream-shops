package com.example.dream_shops.repository;

import com.example.dream_shops.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long userId);


    // Cart findByUser_Id(Long userId);
}
