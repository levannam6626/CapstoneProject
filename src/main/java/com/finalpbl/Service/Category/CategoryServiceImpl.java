package com.finalpbl.Service.Category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalpbl.Model.Category;
import com.finalpbl.Model.Products;
import com.finalpbl.Repository.CategoryRepository;
import com.finalpbl.Repository.ProductsRepository;

@Service
public class CategoryServiceImpl implements ICategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public List<Category> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public Category getCategoryByID(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(null);
        return category;
    }

    @Override
    public String addCategory(Category category) {
        Category categoryCheck = categoryRepository.findByCategoryName(category.getCategoryName()).orElse(null);
        if(categoryCheck == null)
        {
            Category category2 = new Category();
            category2.setCategoryName(category.getCategoryName());
            categoryRepository.save(category2);
            return "OK";
        }
        return "Error";
        
    }

    @Override
    public String deleteCategory(long id) {
        Category categoryValidate = categoryRepository.findById(id).orElseThrow(null);
       
        if(categoryValidate != null)
        {
            List<Products> products = productsRepository.findAllByCategoryId(id);
            for (Products p:products)
            {
                productsRepository.delete(p);
            }
            categoryRepository.delete(categoryValidate);
            return "Delete Success";
        }
        return "Cannot find Category. Delete failure!";
    }
    
}
