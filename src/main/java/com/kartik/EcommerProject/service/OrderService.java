package com.kartik.EcommerProject.service;

import com.kartik.EcommerProject.dto.OrderDTO;
import com.kartik.EcommerProject.dto.OrderItemDTO;
import com.kartik.EcommerProject.exception.ResourceNotFoundException;
import com.kartik.EcommerProject.model.*;
import com.kartik.EcommerProject.repo.OrderRepository;
import com.kartik.EcommerProject.repo.ProductRepository;
import com.kartik.EcommerProject.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderDTO placeOrder(Long userId, Map<Long, Integer> productQuantities, BigDecimal totalAmount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Orders order = Orders.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .totalAmount(totalAmount)
                .orderItems(new ArrayList<>())
                .build();

        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", entry.getKey()));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(entry.getValue())
                    .build();

            order.getOrderItems().add(orderItem);
            orderItemDTOs.add(OrderItemDTO.builder()
                    .productName(product.getName())
                    .productPrice(product.getPrice())
                    .quantity(entry.getValue())
                    .build());
        }

        Orders savedOrder = orderRepository.save(order);

        return OrderDTO.builder()
                .id(savedOrder.getId())
                .totalAmount(savedOrder.getTotalAmount())
                .status(savedOrder.getStatus().name())
                .orderDate(savedOrder.getOrderDate())
                .orderItems(orderItemDTOs)
                .build();
    }

    public Page<OrderDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(this::convertToDTO);
    }

    public List<OrderDTO> getOrderByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return orderRepository.findByUserOrderByOrderDateDesc(user).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Orders order) {
        List<OrderItemDTO> itemDTOs = order.getOrderItems().stream()
                .map(item -> OrderItemDTO.builder()
                        .productName(item.getProduct().getName())
                        .productPrice(item.getProduct().getPrice())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .orderDate(order.getOrderDate())
                .username(order.getUser().getName())
                .email(order.getUser().getEmail())
                .orderItems(itemDTOs)
                .build();
    }
}
