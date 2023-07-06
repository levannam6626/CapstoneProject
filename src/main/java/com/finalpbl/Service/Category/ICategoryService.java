package com.finalpbl.Service.Category;

import java.util.List;

import com.finalpbl.Model.Category;

public interface ICategoryService {
    public List<Category> getAllCategory();
    public Category getCategoryByID(long id);
    public String addCategory(Category category);
    public String deleteCategory(long id);
}
