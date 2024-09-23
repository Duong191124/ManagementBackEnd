package com.example.demo.service.Impl;

import com.example.demo.models.Role;
import com.example.demo.repository.RoleRepo;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public List<Role> getAll() {
        return roleRepo.findAll();
    }
}
