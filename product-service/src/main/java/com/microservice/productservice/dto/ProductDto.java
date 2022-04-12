package com.microservice.productservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String productId;
    private String productName;
    private String productDesc;
    private Long price;
    private boolean isProductAvailable;
}

