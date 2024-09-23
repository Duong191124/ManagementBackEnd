package com.example.demo.service.Impl;

import com.example.demo.dto.UserDTO;
import com.example.demo.models.Cart;
import com.example.demo.models.Role;
import com.example.demo.models.Users;
import com.example.demo.repository.CartRepo;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserService;
import com.example.demo.util.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private CartRepo cartRepo;

    @Override
    public List<Users> getAll() {
        return userRepo.findAll();
    }

    @Override
    public Users save(UserDTO userDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Role existingRole = roleRepo.findById(2).orElse(null);
        try{
            Users newUser = Users
                            .builder()
                            .username(userDTO.getUsername())
                            .password(passwordEncoder.encode(userDTO.getPassword()))
                            .name(userDTO.getName())
                            .status(1)
                            .role(existingRole)
                            .build();
            newUser = userRepo.save(newUser);
            //Tạo mới giỏ hàng cho người dùng
            Cart cart = Cart.builder()
                    .users(newUser)
                    .build();
            cartRepo.save(cart);
            
            newUser.setCart(cart);

            newUser = userRepo.save(newUser);

            return newUser;
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Username " + userDTO.getUsername() + " is already taken", e);
        }
    }

    @Override
    public Users update(Integer id, UserDTO userDTO) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Users existingUser = userRepo.findById(id).get();
        Role existingRole = roleRepo.findById(userDTO.getRoleId()).orElseThrow(() -> new Exception("null role"));
        existingUser.setName(userDTO.getName());
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        existingUser.setStatus(userDTO.getStatus());
        existingUser.setRole(existingRole);
        return userRepo.save(existingUser);
    }

    @Override
    public Users getById(Integer id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        Users existingUser = getById(id);
        userRepo.delete(existingUser);
    }
}
