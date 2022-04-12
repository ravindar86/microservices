package com.microservice.productservice.repository;

import com.microservice.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

    Product findByProductId(String productId);
}
