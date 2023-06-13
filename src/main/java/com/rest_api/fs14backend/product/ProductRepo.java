package com.rest_api.fs14backend.product;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findAllByTitleContainingIgnoreCase(String title);
    List<Product> findAllByTitleContainingIgnoreCaseAndCategoryId(String title, Long categoryId);
}