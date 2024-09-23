package com.example.demo.controller;

import com.example.demo.response.MessageResponse;
import com.example.demo.service.Impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @GetMapping("role")
    public ResponseEntity<?> getAllRole(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.builder()
                        .message("Lay thong tin thanh cong")
                        .status(HttpStatus.OK.value())
                        .data(roleService.getAll())
                        .build());
    }

}
