package com.example.demo.service.Impl;

import com.example.demo.dto.CartDetailDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.models.*;
import com.example.demo.repository.OrderDetailRepo;
import com.example.demo.repository.OrderRepo;
import com.example.demo.repository.ProductRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.response.OrderResponse;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Page<OrderResponse> getAll(PageRequest pageRequest) {
        Page<Order> orders = orderRepo.findAll(pageRequest);
        return orders.map(OrderResponse::fromOrderResponse);
    }

    @Override
    public Order save(OrderDTO orderDTO) throws Exception {
        Users existingUser = userRepo.findById(orderDTO.getUserId()).orElseThrow(() -> new Exception(""));
        Order newOrder = Order.builder()
                .totalPrice(orderDTO.getTotalPrice())
                .address(orderDTO.getAddress())
                .users(existingUser)
                .active(true)
                .build();
        newOrder.setStatus(OrderStatus.PROCESS);
        newOrder = orderRepo.save(newOrder);
        List<OrderDetail> orderDetails = new ArrayList<>();
        double total_money = 0.0;
        for(CartDetailDTO cartDetailDTO : orderDTO.getCartDetail()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(newOrder);

            Integer productId = cartDetailDTO.getProductId();
            int quantity = cartDetailDTO.getQuantity();
            double price = cartDetailDTO.getPrice();

            Product product = productRepo.findById(productId).orElseThrow(() -> new Exception(""));

            orderDetail.setProduct(product);
            orderDetail.setQuantity(quantity);
            orderDetail.setPrice(price);

            total_money += price * quantity;

            orderDetails.add(orderDetail);
        }
        orderDetailRepo.saveAll(orderDetails);
        newOrder.setOrderDetails(orderDetails);
        newOrder.setTotalPrice(total_money);
        orderRepo.save(newOrder);
        return newOrder;
    }

    @Override
    public Order update(Integer id, OrderDTO orderDTO) throws Exception {
        Users existingUser = userRepo.findById(orderDTO.getUserId()).orElseThrow(() -> new Exception(""));
        Order existingOrder = orderRepo.findById(id).orElseThrow(() -> new Exception(""));

        existingOrder.setTotalPrice(orderDTO.getTotalPrice());
        existingOrder.setStatus(orderDTO.getStatus());
        existingOrder.setAddress(orderDTO.getAddress());
        existingOrder.setActive(orderDTO.isActive());
        existingOrder.setUsers(existingUser);

        return orderRepo.save(existingOrder);
    }

    @Override
    public void delete(Integer id) throws Exception {
        Order existingOrder = orderRepo.findById(id).orElseThrow(() -> new Exception(""));
        if(existingOrder != null){
            existingOrder.setActive(false);
            orderRepo.save(existingOrder);
        }
    }

    @Override
    public Order getById(Integer id) throws Exception {
        return orderRepo.findById(id).orElseThrow(() -> new Exception(""));
    }
}
