package com.microservice.pricingservice.controller;

import com.microservice.pricingservice.dto.ProductPriceDto;
import com.microservice.pricingservice.service.PricingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@AllArgsConstructor
@RequestMapping("/pricing-service")
public class PricingController {

    private PricingService pricingService;

    @PostMapping("/add")
    public ProductPriceDto savePricing(@RequestBody ProductPriceDto productPriceDto){
       return pricingService.savePrice(productPriceDto);
    }

    @PostMapping("/update")
    public ProductPriceDto updatePricing(@RequestBody ProductPriceDto productPriceDto){
        return pricingService.updatePrice(productPriceDto);
    }

    @GetMapping("/product/{productId}")
    public ProductPriceDto getPrice(@PathVariable String productId) {
        return pricingService.fetchPrice(productId);
    }
}
