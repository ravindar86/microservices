package com.microservice.productservice.clients;

import com.microservice.productservice.dto.ProductPriceDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="PRICING-SERVICE")
public interface PricingClient {

    Logger logger = LoggerFactory.getLogger(PricingClient.class);

    @GetMapping("/pricing-service/product/{productId}")
    @CircuitBreaker(name="pricing-service", fallbackMethod = "getPricingFallback")
    ProductPriceDto getPrice(@PathVariable String productId);

    @PostMapping("/pricing-service/add")
 //   @CircuitBreaker(name="pricing-service-add", fallbackMethod = "getPricingFallback")
    ProductPriceDto addPrice(@RequestBody ProductPriceDto productPriceDto);

    default ProductPriceDto getPricingFallback(String id, Throwable cause){
        logger.error(cause.getLocalizedMessage());

        return new ProductPriceDto();
    }
}
