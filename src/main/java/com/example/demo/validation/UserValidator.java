package com.example.demo.validation;

import com.example.demo.dto.UserDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserValidator implements ConstraintValidator<ValidUser, UserDTO> {

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext context) {
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            context.buildConstraintViolationWithTemplate("Username cannot be empty")
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        }

        if(userDTO.getUsername().length() < 3){
            context.buildConstraintViolationWithTemplate("username must be have 3 character")
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        }

        if (userDTO.getPassword() == null) {
            context.buildConstraintViolationWithTemplate("Password cannot be empty")
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        }
        if (userDTO.getPassword().length() < 3) {
            context.buildConstraintViolationWithTemplate("Password must be have 3 character")
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
