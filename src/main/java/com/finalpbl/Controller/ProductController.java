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

import com.finalpbl.Model.Products;
import com.finalpbl.Service.Products.IProductService;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin
public class ProductController {
    
    @Autowired
    private IProductService iProductService;

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllProducts()
    {
        List<Products> products = iProductService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProductbyID(@PathVariable(name = "id") Long id)
    {
        Products product = iProductService.getProductbyID(id);
        return ResponseEntity.ok(product);
    }

    @RequestMapping(path = "", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> addProduct(@ModelAttribute Products product)
    {
        String msg = iProductService.addProduct(product);
        if(msg.equals("STATUS OK") )
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
