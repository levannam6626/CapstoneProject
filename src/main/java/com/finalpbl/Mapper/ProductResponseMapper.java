package com.finalpbl.Mapper;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.finalpbl.Dto.Product.ProductDto;
import com.finalpbl.Model.Products;

@Service
public class ProductResponseMapper implements Function<Products, ProductDto>{
    
    @Override
    public ProductDto apply(Products product) {
        return new ProductDto(
            product.getProductId(),
            product.getCategory().getId(),
            product.getProductName(),
            product.getProductDescription(),
            product.getProductImage(),
            product.getUpdateDate(),
            product.getProductPrice(),
            product.getProductQuantity());
    }
}
