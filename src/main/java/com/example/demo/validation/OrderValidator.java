package com.example.demo.validation;

import com.example.demo.dto.OrderDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OrderValidator implements ConstraintValidator<ValidOrder, OrderDTO> {
    @Override
    public boolean isValid(OrderDTO orderDTO, ConstraintValidatorContext context) {
        if(orderDTO.getAddress() == null || orderDTO.getAddress().isEmpty()){
            context.buildConstraintViolationWithTemplate("address can't be null")
                    .addPropertyNode("address")
                    .addConstraintViolation();
            return false;
        }
        if(orderDTO.getTotalPrice() < 0){
            context.buildConstraintViolationWithTemplate("totalPrice must be greater than 0")
                    .addPropertyNode("totalPrice")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
