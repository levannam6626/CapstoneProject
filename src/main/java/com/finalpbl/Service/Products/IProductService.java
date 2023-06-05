package com.finalpbl.Service.Products;

import java.util.List;

import com.finalpbl.Model.Products;

public interface IProductService {
    public List<Products> getAllProducts();
    public List<Products> getProductsSearch(String name);
    public Products getProductbyID(Long id);
    public String addProduct(Products product);
}
