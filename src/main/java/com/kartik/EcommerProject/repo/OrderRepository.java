package com.kartik.EcommerProject.repo;

import com.kartik.EcommerProject.model.Orders;
import com.kartik.EcommerProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT o  FROM Orders o JOIN FETCH o.user")
    List<Orders> findAllOrdersWithUsers();

    List<Orders> findByUser(User user);
}
