package com.example.demo.dto;

import lombok.Data;

@Data
public class CartDetailDTO {
    private int quantity;

    private double price;

    private Integer productId;
}
