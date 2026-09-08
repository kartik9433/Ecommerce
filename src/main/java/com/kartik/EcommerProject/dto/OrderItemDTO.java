package com.kartik.EcommerProject.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {

    private String productName;
    private BigDecimal productPrice;
    private int quantity;
}
