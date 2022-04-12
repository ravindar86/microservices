package com.microservice.pricingservice.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

public class ProductPriceKey implements Serializable {

    private Long priceId;
    private String productId;
}
