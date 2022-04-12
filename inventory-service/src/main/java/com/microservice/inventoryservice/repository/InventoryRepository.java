package com.microservice.inventoryservice.repository;

import com.microservice.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface InventoryRepository extends JpaRepository<Inventory, String> {

    @Transactional
    @Modifying
    @Query("update Inventory  set inStock= ?1 where productId= ?2")
    void updateInventory(boolean inStock, String productId);

    Inventory findByProductId(String productId);

    @Query("select case when(count(inv)>0) then true else false end from Inventory inv where inv.productId= ?1")
    Boolean checkInventory(String productId);
}
