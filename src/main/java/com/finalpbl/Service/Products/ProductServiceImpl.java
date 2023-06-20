package com.finalpbl.Service.Products;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.finalpbl.Dto.Product.ProductDto;
import com.finalpbl.Mapper.ProductResponseMapper;
import com.finalpbl.Model.Category;
import com.finalpbl.Model.Products;
import com.finalpbl.Repository.CategoryRepository;
import com.finalpbl.Repository.ProductsRepository;
import com.finalpbl.Service.Category.CategoryServiceImpl;


@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductsRepository productsRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductResponseMapper productMapper;


    private final Cloudinary cloudinary = Singleton.getCloudinary();

    @Override
    public List<ProductDto> getAllProducts() {
        List<Products> products = productsRepository.findAll();
        List<ProductDto> productsResponse = products.stream().map(productMapper).collect(Collectors.toList());
        return productsResponse;
    }

    @Override
    public List<ProductDto> getProductsSearch(String name) {
        List<Products> products = productsRepository.getProductsSearch(name);
        List<ProductDto> productsResponse = products.stream().map(productMapper).collect(Collectors.toList());
        return productsResponse;
    }

    @Override
    public List<ProductDto> getProductsByCategoryName(String name) {
        Category category = categoryRepository.findByCategoryName(name).orElse(null);
        List<Products> products = productsRepository.getProductsByCategoryId(category.getId());
        List<ProductDto> productsResponse = products.stream().map(productMapper).collect(Collectors.toList());
        return productsResponse;
    }

    @Override
    public ProductDto getProductbyID(Long id) {
        Products product = productsRepository.findById(id).orElseThrow(null);
        ProductDto productResponse = 
            new ProductDto(
                product.getProductId(),
                product.getCategory().getId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getProductImage(),
                product.getUpdateDate(),
                product.getProductPrice(),
                product.getProductQuantity()
            );
        return productResponse;
    }

    public String uploadImg(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String publicId = uploadResult.get("public_id").toString();
            return publicId;
        } catch (Exception ex) {
            return "" + ex;
        }
    }

    public boolean deleteImg(String id) {
        try {
            Map result = cloudinary.uploader()
                    .destroy(id,
                            ObjectUtils.emptyMap());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public String addProduct(ProductDto productRequest, MultipartFile file) throws ParseException {
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow();
        Products productValidate = productsRepository.findByProductName(productRequest.getProductName()).orElse(null);
        if(productValidate == null)
        {
            Products product = new Products();
            product.setProductName(productRequest.getProductName());
            product.setProductImage(uploadImg(file));
            product.setProductPrice(productRequest.getProductPrice());
            product.setUpdateDate(productRequest.getUpdateDate());
            product.setProductDescription(productRequest.getProductDescription());
            product.setCategory(category);
            product.setIsDeleted(false);
            product.setProductQuantity(productRequest.getProductQuantity());
            productsRepository.save(product);
            return "CREATE SUCCESS";
        }
        return "FAILED WHEN ADD PRODUCT";
    }
    @Override
    public String editProduct(ProductDto productRequest, MultipartFile file) throws ParseException {
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow();
        Products productValidate = productsRepository.findProductsByProductIdAndIsDeleted(productRequest.getProductId(), false);
        if(productValidate == null) {
            return "PRODUCT DOES NOT EXIST";
        } else {
            productValidate.setProductName(productRequest.getProductName());
            productValidate.setCategory(category);
            productValidate.setProductDescription(productRequest.getProductDescription());
            productValidate.setProductPrice(productRequest.getProductPrice());
            productValidate.setProductQuantity(productRequest.getProductQuantity());
            productValidate.setUpdateDate(productRequest.getUpdateDate());
            productValidate.setQuantity(productRequest.getQuantity());
            if(file != null) {
                productValidate.setProductImage(uploadImg(file));
                deleteImg(productRequest.getProductImage());
            }
            productsRepository.save(productValidate);
            return "EDIT SUCCESS";
        }
    }
    @Override
    public String deleteProductsByid(List<Long> ids) {
        for (Long id : ids)
        {
            Products product = productsRepository.findById(id).orElseThrow(null);
            product.setIsDeleted(true);
            productsRepository.save(product);   
        }
        return "Delete Success";
    }
}

