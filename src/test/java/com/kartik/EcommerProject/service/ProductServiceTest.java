package com.kartik.EcommerProject.service;

import com.kartik.EcommerProject.exception.ResourceNotFoundException;
import com.kartik.EcommerProject.model.Product;
import com.kartik.EcommerProject.repo.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void addProduct_shouldSaveAndReturnProduct() {
        Product product = Product.builder()
                .name("T-Shirt")
                .description("Cotton t-shirt")
                .price(new BigDecimal("499.00"))
                .imageUrl("http://localhost/img.jpg")
                .category("Clothing")
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.addProduct(product);

        assertNotNull(result);
        assertEquals("T-Shirt", result.getName());
        assertEquals(new BigDecimal("499.00"), result.getPrice());
    }

    @Test
    void getProductById_shouldReturnProduct() {
        Product product = Product.builder().id(1L).name("Shoes").build();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Shoes", result.getName());
    }

    @Test
    void getProductById_shouldThrowResourceNotFound_whenNotExists() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(99L));
    }

    @Test
    void deleteProduct_shouldDeleteWhenExists() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProduct_shouldThrowResourceNotFound_whenNotExists() {
        when(productRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(99L));
    }

    @Test
    void getAllProducts_shouldReturnPage() {
        Product product = Product.builder().id(1L).name("Book").build();
        Page<Product> page = new PageImpl<>(List.of(product));

        when(productRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Product> result = productService.getAllProducts(PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals("Book", result.getContent().get(0).getName());
    }
}
