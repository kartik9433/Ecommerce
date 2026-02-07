package com.kartik.EcommerProject.service;

import com.kartik.EcommerProject.model.Product;
import com.kartik.EcommerProject.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public List<Product> getAllProducts() {
        return   productRepository.findAll();
    }

    @Cacheable(value="product",key="#id")
    public Product getProductById(Long id) {
       return productRepository.findById(id).orElseThrow(null);
    }

    @CachePut(value = "product",key = "#result.id")
    public Product addProduct(Product product) {
       return productRepository.save(product);
    }

    @CacheEvict(value = "product",key = "#id")
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
