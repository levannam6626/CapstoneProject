package com.finalpbl.Service.Products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.finalpbl.Model.Products;
import com.finalpbl.Repository.ProductsRepository;

public class ProductService implements IProductService{

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public List<Products> getAllProducts() {
        List<Products> products = productsRepository.findAll();
        return products;
    }

    @Override
    public Products getProductbyID(Long id) {
        Products product = productsRepository.findById(id).orElseThrow(null);
        return product;
    }

    @Override
    public String addProduct(Products product) {
        Products productValidate = productsRepository.findByProduct_name(product.getProduct_name()).orElseThrow(null);
        if(productValidate == null)
        {
            Products product1 = new Products();
            product1.setProduct_name(product.getProduct_name());
            product1.setProduct_image(product.getProduct_image());
            product1.setProduct_price(product.getProduct_price());
            product1.setUpdate_date(product.getUpdate_date());
            product1.setProduct_description(product.getProduct_description());
            product1.setCategory(product.getCategory());
            productsRepository.save(product1);
            return "STATUS OK";
        }
        return "FAILED WHEN ADD PRODUCT";
    }
    
}
