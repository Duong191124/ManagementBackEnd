package com.example.demo.repository;

import com.example.demo.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE (:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword%)")
    Page<Product> searchProduct(@Param("keyword") String keyword, Pageable pageable);
}
