package com.example.demo.dto;

import com.example.demo.validation.ValidUser;
import lombok.Data;

@Data
@ValidUser
public class UserDTO {

    private String username;

    private String password;

    private String name;

    private int status;

    private Integer roleId;

    private Integer cartId;

}
