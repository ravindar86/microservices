package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.dto.InventoryDto;
import com.microservice.inventoryservice.entity.Inventory;
import com.microservice.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class InventoryService {

    private InventoryRepository inventoryRepository;

    /**
     *
     * @param inventoryDto
     * @return
     */
    public InventoryDto add(InventoryDto inventoryDto){
        ModelMapper mapper= new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Inventory inventory =  mapper.map(inventoryDto, Inventory.class);

        Inventory savedInventory = inventoryRepository.save(inventory);

        return mapper.map(savedInventory, InventoryDto.class);
    }

    /**
     *
     * @param productId
     * @return
     */
    public InventoryDto remove(String productId){
        ModelMapper mapper= new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        inventoryRepository.updateInventory(Boolean.FALSE, productId);
        Inventory updatedInventory = inventoryRepository.findByProductId(productId);

        return mapper.map(updatedInventory, InventoryDto.class);
    }

    /**
     *
     * @param productId
     * @return
     */
    public InventoryDto update(String productId){
        ModelMapper mapper= new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        inventoryRepository.updateInventory(Boolean.TRUE, productId);
        Inventory updatedInventory = inventoryRepository.findByProductId(productId);

        return mapper.map(updatedInventory, InventoryDto.class);
    }

    public InventoryDto fetchProductInventory(String productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        return new ModelMapper().map(inventory, InventoryDto.class);
    }

    public Boolean checkInventory(String productId){
        return inventoryRepository.checkInventory(productId);
    }
}
