package com.finalpbl.Service.Products;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.finalpbl.Dto.Product.ProductDto;

public interface IProductService {
    public List<ProductDto> getAllProducts();
    public List<ProductDto> getProductsSearch(String name);
    public ProductDto getProductbyID(Long id);
    public String addProduct(ProductDto product, MultipartFile file) throws ParseException;
    public String editProduct(ProductDto product, MultipartFile file) throws ParseException;
    public List<ProductDto> getProductsByCategoryName(String name);
    public String deleteProductsByid(List<Long> ids);
}
