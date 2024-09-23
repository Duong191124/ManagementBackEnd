package com.example.demo.service;

import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.models.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    OrderDetail createOrderDetail (OrderDetailDTO orderDetailDTO) throws Exception;

    OrderDetail getOrderDetailById (Integer id) throws Exception;

    List<OrderDetail> findByOrderId (Integer orderId);

    OrderDetail updateOrderDetail (OrderDetailDTO orderDetailDTO, Integer id) throws Exception;

    void deleteOrderDetail (Integer id);
}
