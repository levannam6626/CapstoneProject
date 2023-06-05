package com.finalpbl.Service.Products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalpbl.Model.Products;
import com.finalpbl.Repository.ProductsRepository;

@Service
public class ProductService implements IProductService{

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public List<Products> getAllProducts() {
        List<Products> products = productsRepository.findAll();
        return products;
    }

    @Override
    public List<Products> getProductsSearch(String name) {
        List<Products> products = productsRepository.getProductsSearch(name);
        return products;
    }

    @Override
    public Products getProductbyID(Long id) {
        Products product = productsRepository.findById(id).orElseThrow(null);
        return product;
    }

    @Override
    public String addProduct(Products product) {
        Products productValidate = productsRepository.findByProductName(product.getProductName()).orElse(null);
        if(productValidate == null)
        {
            Products product1 = new Products();
            product1.setProductName(product.getProductName());
            product1.setProductImage(product.getProductImage());
            product1.setProductPrice(product.getProductPrice());
            product1.setUpdateDate(product.getUpdateDate());
            product1.setProductDescription(product.getProductDescription());
            product1.setCategory(product.getCategory());
            productsRepository.save(product1);
            return "STATUS OK";
        }
        return "FAILED WHEN ADD PRODUCT";
    }
    
}
