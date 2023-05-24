package com.finalpbl.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.finalpbl.Model.Category;
import com.finalpbl.Service.Category.ICategoryService;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin
public class CategoryController {
    
    @Autowired
    private ICategoryService categoryService;

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllCategory()
    {
        List<Category> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getCategoryByID(@PathVariable(name = "id") Long id)
    {
        Category category = categoryService.getCategoryByID(id);
        return ResponseEntity.ok(category);
    }

    @RequestMapping(path = "", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> addProduct(@ModelAttribute Category category)
    {
        String msg = categoryService.addCategory(category);
        if(msg.equals("OK") )
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
