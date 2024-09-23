package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.models.Product;
import com.example.demo.response.MessageResponse;
import com.example.demo.response.ProductResponse;
import com.example.demo.service.Impl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductController {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("product")
    public ResponseEntity<?> getAllProduct(@RequestParam(name = "page", defaultValue = "1")int page, @RequestParam("keyword") String keyword){
        PageRequest pageRequest = PageRequest.of(page-1, 10);
        Page<ProductResponse> productList = productService.getAll(pageRequest, keyword);
        List<ProductResponse> content = productList.getContent();
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.builder()
                        .message("Lay thong tin thanh cong")
                        .status(HttpStatus.OK.value())
                        .data(content)
                        .build());
    }

    @PostMapping("product")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO){
        Product newProduct = productService.save(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.builder()
                .message("Create product successfully")
                .status(HttpStatus.CREATED.value())
                .data(newProduct)
                .build());
    }

    @PostMapping(value = "product/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> uploadImageWithProductId(@PathVariable("id") int id, @ModelAttribute("file") MultipartFile file){
        try {
            if(file.getSize() > MAX_FILE_SIZE){
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("file is so large! Maximum size is 5MB");
            }
            Product updateProduct = productService.saveProductWithImage(id, file);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(MessageResponse.builder()
                            .message("Upload image successfully")
                            .status(HttpStatus.OK.value())
                            .data(updateProduct)
                            .build());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "product/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateProduct
            (@PathVariable("id") int id,
             @ModelAttribute ProductDTO productDTO,
             @ModelAttribute("file") MultipartFile file)
    {
        if(file.getSize() > MAX_FILE_SIZE){
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("file is so large! Maximum size is 5MB");
        }
        try {
            Product updatedProduct = productService.updateProductWithImage(id, productDTO, file);
            ProductResponse productResponse = ProductResponse.fromProductResponse(updatedProduct);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(MessageResponse.builder()
                            .message("Update product successfully")
                            .status(HttpStatus.OK.value())
                            .data(productResponse)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
