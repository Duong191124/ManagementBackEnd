package com.example.demo.repository;

import com.example.demo.models.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepo extends JpaRepository<CartDetail, Integer> {
}
