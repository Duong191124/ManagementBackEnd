package com.example.demo.dto;

import com.example.demo.validation.ValidProduct;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@ValidProduct
public class ProductDTO {

    private String code;

    private String name;

    private MultipartFile image;

    private String description;

    private double price;

    private int status;

    private Integer categoryId;
}
