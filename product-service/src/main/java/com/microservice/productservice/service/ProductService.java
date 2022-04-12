package com.microservice.productservice.service;

import com.microservice.productservice.dto.ProductDto;
import com.microservice.productservice.entity.Product;
import com.microservice.productservice.repository.ProductRepository;
import com.microservice.productservice.utils.MapperUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class ProductService {

    private ProductRepository productRepository;

    /**
     * Fetch All Products
     * @return
     */
    public List<ProductDto> fetchProducts(){

        List<Product> productList = productRepository.findAll();
        return MapperUtils.mapAll(productList, ProductDto.class);
    }

    /**
     * Fetch Product
     * @return
     */
    public ProductDto fetchProduct(String productId){

        Product product = productRepository.findByProductId(productId);
        return new ModelMapper().map(product, ProductDto.class);
    }

    /**
     * Save product
     * @param productDto
     * @return
     */
    public ProductDto saveProduct(ProductDto productDto){

       // productDto.setProductId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Product product = mapper.map(productDto,Product.class);
        productRepository.save(product);

        return mapper.map(product, ProductDto.class);
    }

    /**
     * Save product
     * @param productDto
     * @return
     */
    public ProductDto updateProduct(ProductDto productDto){

        //productDto.setProductId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Product product = mapper.map(productDto,Product.class);
        productRepository.save(product);

        return mapper.map(product, ProductDto.class);
    }


}
