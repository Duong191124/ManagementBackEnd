package com.example.demo.validation;

import com.example.demo.dto.ProductDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductValidator implements ConstraintValidator<ValidProduct, ProductDTO> {
    @Override
    public boolean isValid(ProductDTO productDTO, ConstraintValidatorContext context) {
        if(productDTO.getCode() == null || productDTO.getCode().isEmpty()){
            context.buildConstraintViolationWithTemplate("Code can't be empty")
                    .addPropertyNode("code")
                    .addConstraintViolation();
            return false;
        }
        if(productDTO.getName() == null || productDTO.getName().isEmpty()){
            context.buildConstraintViolationWithTemplate("Name can't be empty")
                    .addPropertyNode("name")
                    .addConstraintViolation();
            return false;
        }
        if(productDTO.getName().length() < 3){
            context.buildConstraintViolationWithTemplate("Name must be have 3 character")
                    .addPropertyNode("name")
                    .addConstraintViolation();
            return false;
        }
        if(productDTO.getPrice() < 0){
            context.buildConstraintViolationWithTemplate("Price must be greater than 0")
                    .addPropertyNode("price")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
