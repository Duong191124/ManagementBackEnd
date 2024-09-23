package com.example.demo.dto;

import com.example.demo.validation.ValidCategory;
import lombok.Data;

@Data
@ValidCategory
public class CategoryDTO {
    private String name;
}
