package com.kartik.EcommerProject.repo;

import com.kartik.EcommerProject.model.Orders;
import com.kartik.EcommerProject.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.product"})
    List<Orders> findByUserOrderByOrderDateDesc(User user);

    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.product"})
    List<Orders> findAllByOrderByOrderDateDesc();
}
