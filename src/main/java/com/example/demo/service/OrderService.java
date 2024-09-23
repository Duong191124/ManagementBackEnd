package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.models.Order;
import com.example.demo.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface OrderService {
    Page<OrderResponse> getAll(PageRequest pageRequest);

    Order save(OrderDTO orderDTO) throws Exception;

    Order update(Integer id, OrderDTO orderDTO) throws Exception;

    void delete(Integer id) throws Exception;

    Order getById(Integer id) throws Exception;
}
