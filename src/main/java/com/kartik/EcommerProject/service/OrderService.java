package com.kartik.EcommerProject.service;

import com.kartik.EcommerProject.dto.OrderDTO;
import com.kartik.EcommerProject.dto.OrderItemDTO;
import com.kartik.EcommerProject.model.OrderItem;
import com.kartik.EcommerProject.model.Orders;
import com.kartik.EcommerProject.model.Product;
import com.kartik.EcommerProject.model.User;
import com.kartik.EcommerProject.repo.OrderRepository;
import com.kartik.EcommerProject.repo.ProductRepository;
import com.kartik.EcommerProject.repo.UserRepository;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public OrderDTO placeOrder(Long userId, Map<Long, Integer> productQuantities, double totalAmount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Orders order = new Orders();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus("pending");
        order.setTotalAmount(totalAmount);

        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(entry.getValue());

            orderItems.add(orderItem);
            orderItemDTOS.add(new OrderItemDTO(entry.getValue(), product.getPrice(), product.getName()));
        }

        order.setOrderItems(orderItems);
        Orders savedOrder = orderRepository.save(order);

        return new OrderDTO(savedOrder.getId(), savedOrder.getTotalAmount(),
                savedOrder.getStatus(), savedOrder.getOrderDate(), orderItemDTOS);
    }

    public List<OrderDTO> getAllOrders() {
        List<Orders> orders = orderRepository.findAllOrdersWithUsers();
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Orders order) {
        List<OrderItemDTO> orderItemDTOS = order.getOrderItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getQuantity(),
                        item.getProduct().getPrice(),
                        item.getProduct().getName()))
                .collect(Collectors.toList());

        return new OrderDTO(order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getOrderDate(),
                orderItemDTOS);
    }

    public List<OrderDTO> getOrderByUser(Long userId) {
       Optional<User>userOp = userRepository.findById(userId);
       if (userOp.isEmpty()){
           throw new RuntimeException("User not found");
       }
       User user = userOp.get();
      List<Orders> ordersList =  orderRepository.findByUser(user);
      return ordersList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
