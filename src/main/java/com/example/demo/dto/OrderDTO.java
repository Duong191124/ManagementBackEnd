package com.example.demo.dto;

import com.example.demo.models.OrderStatus;
import com.example.demo.validation.ValidOrder;
import lombok.Data;

import java.util.List;

@Data
@ValidOrder
public class OrderDTO {

    private Double totalPrice;

    private OrderStatus status;

    private String address;

    private boolean active;

    private Integer userId;

    private List<CartDetailDTO> cartDetail;
}
