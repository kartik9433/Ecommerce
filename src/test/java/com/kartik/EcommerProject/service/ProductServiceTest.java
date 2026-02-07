package com.kartik.EcommerProject.service;

import com.kartik.EcommerProject.model.Product;
import com.kartik.EcommerProject.repo.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @BeforeAll
    public static  void init(){
        System.out.println("before all");
    }

    @BeforeEach
    public void initEachTest(){
        System.out.println("beforeEach");
    }

    @Test
    void addProduct(){

        Product p = new Product();
        p.setId(1L);
        p.setName("kartik");
        p.setDescription("best");
        p.setPrice(9000.00);
        p.setImageUrl("http:localhost//img.jpg");
        p.setCategory("game");
        Mockito.when(productRepository.save(p)).thenReturn(p);
        Product product = productService.addProduct(p);
        System.out.println("my first unit test");

        Assertions.assertNotNull(product);
        Assertions.assertEquals(p.getId(),product.getId());
        Assertions.assertEquals(p.getName(),product.getName());
    }

    @Test
    public void dummyTest(){
        System.out.println("dummytest");
    }

    @AfterAll
    public static void Destroy(){
        System.out.println("after all");
    }

    @AfterEach
    public void cleanup(){
        System.out.println("AfterEach");
    }

    @Test
    public void deleteProduct(){
        doNothing().when(productRepository).deleteById(1L);
         productService.deleteProduct(1L);
         Mockito.verify(productRepository,Mockito.times(1)).deleteById(1L);
    }



}
