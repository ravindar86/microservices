package com.microservice.productservice.clients;

import com.microservice.productservice.dto.InventoryDto;
import com.microservice.productservice.dto.ProductPriceDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="INVENTORY-SERVICE")
public interface InventoryClient {

    Logger logger = LoggerFactory.getLogger(InventoryClient.class);

    @GetMapping("/inventory-service/inventory/{productId}")
    @CircuitBreaker(name="inventory-service", fallbackMethod = "getInventoryFallback")
    InventoryDto getInventory(@PathVariable String productId);

    @GetMapping("/inventory-service/add")
    InventoryDto addInventory(@RequestBody InventoryDto inventoryDto);

    default InventoryDto getInventoryFallback(String id, Throwable cause){
        logger.error(cause.getLocalizedMessage());

        return new InventoryDto();
    }
}
