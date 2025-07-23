package com.example.dream_shops.repository;

import com.example.dream_shops.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);


    User findByEmail(String email);
}
