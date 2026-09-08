package com.kartik.EcommerProject.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long id;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime orderDate;
    private String username;
    private String email;
    private List<OrderItemDTO> orderItems;
}
