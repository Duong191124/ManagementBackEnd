package com.example.demo.controller;

import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.models.OrderDetail;
import com.example.demo.response.MessageResponse;
import com.example.demo.response.OrderDetailResponse;
import com.example.demo.service.Impl.OrderDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class OrderDetailController {

    @Autowired
    private OrderDetailServiceImpl orderDetailService;

    @GetMapping("orderDetail/{id}")
    public ResponseEntity<?> getOrderDetailById(@PathVariable() int id) throws Exception{
        OrderDetail orderDetail = orderDetailService.getOrderDetailById(id);
        return ResponseEntity.ok().body(MessageResponse.builder()
                .message("Lay thong tin thanh cong")
                .status(HttpStatus.OK.value())
                .data(orderDetail)
                .build());
    }

    @GetMapping("orderDetail/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable("orderId") int orderId){
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderDetails
                .stream()
                .map(OrderDetailResponse::fromOrderDetailResponse).toList();
        return ResponseEntity.ok().body(MessageResponse.builder()
                .message("Lay thong tin thanh cong")
                .status(HttpStatus.OK.value())
                .data(orderDetailResponses)
                .build());
    }

    @PostMapping("orderDetail")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) throws Exception{
        OrderDetail orderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.builder()
                .message("Create orderDetail successfully")
                .status(HttpStatus.CREATED.value())
                .data(orderDetail)
                .build());
    }

}
