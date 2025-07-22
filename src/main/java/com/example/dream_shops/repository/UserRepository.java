package com.example.dream_shops.repository;

import com.example.dream_shops.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    boolean existByEmail(String email);
}
