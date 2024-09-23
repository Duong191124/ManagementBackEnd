package com.example.demo.service.Impl;

import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.models.Order;
import com.example.demo.models.OrderDetail;
import com.example.demo.models.Product;
import com.example.demo.repository.OrderDetailRepo;
import com.example.demo.repository.OrderRepo;
import com.example.demo.repository.ProductRepo;
import com.example.demo.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        Order existingOrder = orderRepo.findById(orderDetailDTO.getOrderId()).orElseThrow(() -> new Exception(""));
        Product existingProduct = productRepo.findById(orderDetailDTO.getProductId()).orElseThrow(() -> new Exception(""));

        OrderDetail orderDetail = OrderDetail.builder()
                .order(existingOrder)
                .product(existingProduct)
                .price(orderDetailDTO.getPrice())
                .quantity(orderDetailDTO.getQuantity())
                .build();
        return orderDetailRepo.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetailById(Integer id) throws Exception {
        return orderDetailRepo.findById(id).orElse(null);
    }

    @Override
    public List<OrderDetail> findByOrderId(Integer orderId) {
        return orderDetailRepo.findByOrderId(orderId);
    }

    @Override
    public OrderDetail updateOrderDetail(OrderDetailDTO orderDetailDTO, Integer id) throws Exception {
        OrderDetail existingOrderDetail = orderDetailRepo.findById(id).orElseThrow(() -> new Exception(""));
        Order existingOrder = orderRepo.findById(orderDetailDTO.getOrderId()).orElseThrow(() -> new Exception(""));
        Product existingProduct = productRepo.findById(orderDetailDTO.getProductId()).orElseThrow(() -> new Exception(""));

        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        existingOrderDetail.setQuantity(orderDetailDTO.getQuantity());
        existingOrderDetail.setProduct(existingProduct);

        return orderDetailRepo.save(existingOrderDetail);
    }

    @Override
    public void deleteOrderDetail(Integer id) {
        orderDetailRepo.deleteById(id);
    }
}
