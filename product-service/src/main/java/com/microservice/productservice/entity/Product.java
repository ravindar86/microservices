package com.microservice.productservice.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String productId;
    private String productName;
    private String productDesc;
  //  private Long price;
   // private boolean isProductAvailable;
}
