package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.models.Order;
import com.example.demo.response.MessageResponse;
import com.example.demo.response.OrderResponse;
import com.example.demo.service.Impl.OrderServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("order")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "1") int page){
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        Page<OrderResponse> orderResponses = orderService.getAll(pageRequest);
        List<OrderResponse> content = orderResponses.getContent();
        return ResponseEntity.ok()
                .body(MessageResponse.builder()
                        .message("Lay thong tin thanh cong")
                        .status(HttpStatus.OK.value())
                        .data(content)
                        .build());
    }

    @PostMapping("order")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> create(@Valid @RequestBody OrderDTO orderDTO) throws Exception{
        OrderResponse newOrder = OrderResponse.fromOrderResponse(orderService.save(orderDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.builder()
                        .message("Create order successfully")
                        .status(HttpStatus.CREATED.value())
                        .data(newOrder)
                        .build());
    }

    @PutMapping("order/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody OrderDTO orderDTO) throws Exception{
        Order updateOrder = orderService.update(id, orderDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.builder()
                        .message("Update order successfully")
                        .status(HttpStatus.OK.value())
                        .data(updateOrder)
                        .build());
    }

    @DeleteMapping("order/{id}")
    public ResponseEntity<?> delete(@PathVariable() int id) throws Exception{
        orderService.delete(id);
        return ResponseEntity.ok().body("Delete order with id = " + id + " successfully");
    }

}
