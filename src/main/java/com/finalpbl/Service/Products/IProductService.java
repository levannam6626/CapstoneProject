package com.finalpbl.Service.Products;

import java.util.List;

import com.finalpbl.Model.Products;

public interface IProductService {
    public List<Products> getAllProducts();
    public Products getProductbyID(Long id);
    public String addProduct(Products product);
}
