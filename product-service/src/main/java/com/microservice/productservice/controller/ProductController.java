package com.microservice.productservice.controller;

import com.microservice.productservice.clients.InventoryClient;
import com.microservice.productservice.clients.PricingClient;
import com.microservice.productservice.dto.InventoryDto;
import com.microservice.productservice.dto.ProductDto;
import com.microservice.productservice.dto.ProductPriceDto;
import com.microservice.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@RestController
//@RequestMapping("/product-app")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;
   // private RestTemplate restTemplate;
    private final PricingClient pricingClient;
    private final InventoryClient inventoryClient;

    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/status")
    public String test() {
        return "Hello from Product Service";
    }

    @PostMapping("/post/status")
    public String testPost() {
        return "Hello from Product Service via Post";
    }

    @GetMapping("/products")
    public List<ProductDto> getProducts(){

        ProductPriceDto priceDto;
        InventoryDto inventoryDto;

        List<ProductDto> products = productService.fetchProducts();
        for(ProductDto productDto : products) {

           /* priceDto = restTemplate.getForObject("http://localhost:8082/pricing-service/product/"+productDto.getProductId(),
                    ProductPriceDto.class); */

           /* inventoryDto = restTemplate.getForObject("http://localhost:8083/inventory-service/inventory/"+productDto.getProductId(),
                    InventoryDto.class);*/

            logger.info("Fetching pricing details for product - "+productDto.getProductId());
            priceDto = pricingClient.getPrice(productDto.getProductId());

            logger.info("Fetching inventory details for product - "+productDto.getProductId());
            inventoryDto = inventoryClient.getInventory(productDto.getProductId());

            productDto.setPrice(priceDto.getPrice());
            productDto.setProductAvailable(inventoryDto.isInStock());
        }

        return products;
    }

    @GetMapping("/products/{productId}")
    public ProductDto getProduct(@PathVariable String productId){

        ProductDto productDto = productService.fetchProduct(productId);
      //  ProductPriceDto priceDto = restTemplate.getForObject("http://localhost:8082/pricing-service/product/"+productId, ProductPriceDto.class);
         /* inventoryDto = restTemplate.getForObject("http://localhost:8083/inventory-service/inventory/"+productDto.getProductId(),
                    InventoryDto.class);*/

        logger.info("Fetching pricing details for product - "+productDto.getProductId());
        ProductPriceDto priceDto = pricingClient.getPrice(productDto.getProductId());

        logger.info("Fetching inventory details for product - "+productDto.getProductId());
        InventoryDto inventoryDto = inventoryClient.getInventory(productDto.getProductId());

        productDto.setPrice(priceDto.getPrice());
        productDto.setProductAvailable(inventoryDto.isInStock());

        return productDto;
    }

    @PostMapping("/products")
    public ProductDto saveProduct(@RequestBody ProductDto productDto){

        productDto.setProductId(UUID.randomUUID().toString());

        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("pricing");
        Supplier<ProductPriceDto> pricingDto = () ->  pricingClient.addPrice(createPricing(productDto));

      //  ProductPriceDto pricingResultDto = pricingClient.addPrice(createPricing(productDto));
        ProductPriceDto pricingResultDto =circuitBreaker.run(pricingDto, throwable -> handleErrorCase(pricingDto.get().getProductId()));
        InventoryDto inventoryResultDto = inventoryClient.addInventory(createInventory(productDto));

        ProductDto savedProductDto = productService.saveProduct(productDto);

        savedProductDto.setPrice(pricingResultDto.getPrice());
        savedProductDto.setProductAvailable(inventoryResultDto.isInStock());

        return savedProductDto;
    }

    private ProductPriceDto handleErrorCase(String productId) {
        logger.info("No pricing details for product -> "+productId);
        return new ProductPriceDto();
    }

    private InventoryDto createInventory(ProductDto productDto) {
        return new InventoryDto(productDto.getProductId(), productDto.isProductAvailable());
    }

    private ProductPriceDto createPricing(ProductDto productDto) {
        return new ProductPriceDto(productDto.getProductId(), productDto.getPrice());
    }

}
