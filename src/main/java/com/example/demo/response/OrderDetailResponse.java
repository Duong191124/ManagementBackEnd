package com.example.demo.response;

import com.example.demo.models.OrderDetail;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailResponse {
    private int id;

    private int quantity;

    private Double price;

    private Integer orderId;

    private Integer productId;

    public static OrderDetailResponse fromOrderDetailResponse(OrderDetail orderDetail){
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .quantity(orderDetail.getQuantity())
                .price(orderDetail.getPrice())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .build();
    }
}
