package com.kartik.EcommerProject.repo;

import com.kartik.EcommerProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
