package com.microservice.inventoryservice.controller;

import com.microservice.inventoryservice.dto.InventoryDto;
import com.microservice.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/inventory-service")
public class InventoryController {

    private InventoryService inventoryService;

    @PostMapping("/add")
    public InventoryDto addInventory(@RequestBody InventoryDto inventoryDto){
        return inventoryService.add(inventoryDto);
    }

    @PostMapping("/remove/{productId}")
    public InventoryDto removeInventory(@PathVariable String productId){
        return inventoryService.remove(productId);
    }

    @PostMapping("/update/{productId}")
    public InventoryDto updateInventory(@PathVariable String productId){
        return inventoryService.update(productId);
    }

    @GetMapping("/inventory/{productId}")
    public InventoryDto getInventory(@PathVariable String productId) {
        return inventoryService.fetchProductInventory(productId);
    }

    @GetMapping("/checkInventory/{productId}")
    public Boolean checkInventory(@PathVariable String productId) {
        Boolean value =  inventoryService.checkInventory(productId);
        System.out.println("Result :"+value);
        return value;
    }
}
