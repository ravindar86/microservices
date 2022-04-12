package com.microservice.pricingservice.service;

import com.microservice.pricingservice.dto.ProductPriceDto;
import com.microservice.pricingservice.model.ProductPrice;
import com.microservice.pricingservice.repository.PricingRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PricingService {

    private PricingRepository pricingRepository;

    /**
     *
     * @param productId
     * @return
     */
    public ProductPriceDto fetchPrice(String productId){
        ProductPrice productPrice =  pricingRepository.findProductPriceByProductId(productId);
        return new ModelMapper().map(productPrice, ProductPriceDto.class);
    }

    /**
     *
     * @param productPriceDto
     * @return
     */
    public ProductPriceDto savePrice(ProductPriceDto productPriceDto){

        ModelMapper mapper= new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ProductPrice productPrice =  mapper.map(productPriceDto, ProductPrice.class);
        ProductPrice savedProductPrice = null;

        if(pricingRepository.countByProductId(productPrice.getProductId())>0) {
            pricingRepository.updateProductPrice(productPriceDto.getPrice(),productPriceDto.getProductId());
            savedProductPrice = getProductPriceByProductId(productPriceDto.getProductId());
        }else{
            savedProductPrice = pricingRepository.save(productPrice);
        }

        return mapper.map(savedProductPrice, ProductPriceDto.class);
    }

    /**
     *
     * @param productPriceDto
     * @return
     */
    public ProductPriceDto updatePrice(ProductPriceDto productPriceDto){

        ModelMapper mapper= new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        pricingRepository.updateProductPrice(productPriceDto.getPrice(),productPriceDto.getProductId());
        ProductPrice updatedProductPrice = getProductPriceByProductId(productPriceDto.getProductId());

        return mapper.map(updatedProductPrice, ProductPriceDto.class);
    }

    /**
     *
     * @param productId
     * @return
     */
    private ProductPrice getProductPriceByProductId(String productId) {
        return pricingRepository.findProductPriceByProductId(productId);
    }
}
