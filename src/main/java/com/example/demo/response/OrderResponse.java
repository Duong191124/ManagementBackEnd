package com.example.demo.response;

import com.example.demo.dto.CartDetailDTO;
import com.example.demo.models.Order;
import com.example.demo.models.OrderDetail;
import com.example.demo.models.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class OrderResponse {

    private Integer id;

    private Double totalPrice;

    private String address;

    private OrderStatus status;

    private boolean active;

    private Integer userId;

    private List<OrderDetail> orderDetails;

    public static OrderResponse fromOrderResponse(Order order){
            return OrderResponse.builder()
                    .id(order.getId())
                    .totalPrice(order.getTotalPrice())
                    .address(order.getAddress())
                    .status(order.getStatus())
                    .active(order.isActive())
                    .userId(order.getUsers().getId())
                    .orderDetails(order.getOrderDetails())
                    .build();
    }
}
