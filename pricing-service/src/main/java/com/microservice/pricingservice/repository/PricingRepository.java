package com.microservice.pricingservice.repository;

import com.microservice.pricingservice.model.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PricingRepository extends JpaRepository<ProductPrice, Long> {

    ProductPrice findProductPriceByProductId(String productId);

    @Transactional
    @Modifying
    @Query("update ProductPrice p set p.price = ?1 where p.productId = ?2")
    void updateProductPrice(Long price, String productId);

    int countByProductId(String productId);
}
