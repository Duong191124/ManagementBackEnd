package com.example.demo.validation;

import com.example.demo.dto.CategoryDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<ValidCategory, CategoryDTO> {
    @Override
    public boolean isValid(CategoryDTO categoryDTO, ConstraintValidatorContext context) {
        if (categoryDTO.getName() == null || categoryDTO.getName().isEmpty()) {
            context.buildConstraintViolationWithTemplate("name can't be empty")
                    .addPropertyNode("name")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
