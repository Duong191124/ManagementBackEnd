package com.example.demo.response;

import com.example.demo.models.Users;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private int id;

    private String username;

    private String name;

    private int status;

    private String roleName;

    private Integer cartId;

    public static UserResponse fromUserResponse(Users users){
        return UserResponse
                .builder()
                .id(users.getId())
                .username(users.getUsername())
                .name(users.getName())
                .status(users.getStatus())
                .roleName(users.getRole().getName())
                .cartId(users.getCart() != null ? users.getCart().getId() : null)
                .build();
    }
}
