package com.example.demo.controller;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.models.Category;
import com.example.demo.response.MessageResponse;
import com.example.demo.service.Impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("category")
    public ResponseEntity<?> getAllCategory(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.builder()
                        .message("Lay thong tin thanh cong")
                        .status(HttpStatus.OK.value())
                        .data(categoryService.getAll())
                        .build());
    }

    @PostMapping("category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        Category newCategory = categoryService.save(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.builder()
                .message("Create category successfully")
                .status(HttpStatus.CREATED.value())
                .data(newCategory)
                .build());
    }

    @PutMapping("category/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateCategory(@PathVariable("id") int id, CategoryDTO categoryDTO){
        Category updateCategory = categoryService.update(id, categoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.builder()
                .message("Update category successfully")
                .status(HttpStatus.OK.value())
                .data(updateCategory)
                .build());
    }

    @DeleteMapping("category/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") int id){
        categoryService.delete(id);
        return ResponseEntity.ok().body("Delete category with id = " + id + " successfully");
    }

}
