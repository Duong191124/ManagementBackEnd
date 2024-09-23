package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.models.Users;

import java.util.List;

public interface UserService {
    List<Users> getAll();

    Users save(UserDTO userDTO);

    Users update(Integer id, UserDTO userDTO) throws Exception;

    Users getById(Integer id);

    void delete(Integer id);
}
