package com.kartik.EcommerProject.controller;

import com.kartik.EcommerProject.model.Product;
import com.kartik.EcommerProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/getProducts")
    List<Product>getAllProducts(){
        return  productService.getAllProducts();
    }

     @GetMapping("/{Id}")
    public Product getProductById(@PathVariable Long Id){
           return  productService.getProductById(Id);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product){
         return  productService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
            productService.deleteProduct(id);
    }

    
}
