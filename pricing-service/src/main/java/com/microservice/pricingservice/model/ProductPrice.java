package com.microservice.pricingservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ProductPriceKey.class)
public class ProductPrice {

    @Id
    @GeneratedValue
    private Long priceId;
    @Id
    private String productId;
    private Long price;

}
