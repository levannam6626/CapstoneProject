package com.finalpbl.Controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.finalpbl.Dto.ProductDto;
import com.finalpbl.Service.Products.IProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin
public class ProductController {
    
    @Autowired
    private IProductService iProductService;

    @GetMapping(path = "/get-by-search/{name}")
    public ResponseEntity<?> getProductsSearch(@PathVariable(name = "name") String name)
    {
        List<ProductDto> products;
        if(name.equals("all")){
            products = iProductService.getProductsSearch("");
        }
        else{
            products = iProductService.getProductsSearch(name);
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping(path = "/get-by-categoryname/{name}")
    public ResponseEntity<?> getProductsByCategoryName(@PathVariable(name = "name") String name)
    {
        System.out.println(name);
        List<ProductDto> products = iProductService.getProductsByCategoryName(name);
        
        return ResponseEntity.ok(products);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProductbyID(@PathVariable(name = "id") Long id)
    {
        ProductDto product = iProductService.getProductbyID(id);
        return ResponseEntity.ok(product);
    }

    @RequestMapping(path = "/create-product", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> addProduct(@RequestPart(value = "productRequest") ProductDto productRequest,@Valid @RequestPart(value = "file") MultipartFile file)
    {
        String msg ="";
        try {
            msg = iProductService.addProduct(productRequest,file);
            if(msg.equals("CREATE SUCCESS") )
            {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @RequestMapping(path = "/edit-product", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> editProduct(@RequestPart(value = "productRequest") ProductDto productRequest,@ModelAttribute MultipartFile file)
    {
        String msg ="";
        try {
            msg = iProductService.editProduct(productRequest,file);
            if(msg.equals("EDIT SUCCESS") )
            {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping(path = "/delete-product")
    public ResponseEntity<?> deleteProductsByid(@RequestBody List<Long> ids)
    {
        System.out.println(ids);
        String msg = iProductService.deleteProductsByid(ids);
        if(msg.equals("Delete Success"))
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(msg);
    }
}
