package com.finalpbl.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @GetMapping(path = "view/all")
    public ResponseEntity<?> getAllCategory()
    {
        List<Category> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    @GetMapping(path = "view/{id}")
    public ResponseEntity<?> getCategoryByID(@PathVariable(name = "id") Long id)
    {
        Category category = categoryService.getCategoryByID(id);
        return ResponseEntity.ok(category);
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<?> addCategory(@ModelAttribute Category category)
    {
        String msg = categoryService.addCategory(category);
        if(msg.equals("OK") )
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(path = "/delete/{id}")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<?> deleteCategory(@PathVariable(name = "id") Long id)
    {
        String msg = categoryService.deleteCategory(id);
        if(msg.equals("Delete Success"))
        {
            return ResponseEntity.ok().body(msg);
        }
        return ResponseEntity.badRequest().body(msg);
    }
}
