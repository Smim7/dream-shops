package com.example.dream_shops.repository;

import com.example.dream_shops.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    void deleteAllBYCaerId(Long id);

    void deleteAllBYCartId(Long id);
}
