package com.kartik.EcommerProject.dto;

public class OrderItemDTO {

     private String productName;

     private double productPrice;

     private  int quantity;

    public OrderItemDTO(){};

    public OrderItemDTO(int quantity, double productPrice, String productName) {
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
