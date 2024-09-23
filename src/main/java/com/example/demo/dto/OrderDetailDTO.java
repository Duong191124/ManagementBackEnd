package com.example.demo.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {

    private int quantity;

    private Double price;

    private Integer orderId;

    private Integer productId;

}
