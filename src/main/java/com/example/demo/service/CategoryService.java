package com.example.demo.service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.models.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category save(CategoryDTO categoryDTO);

    Category update(Integer id, CategoryDTO categoryDTO);

    Category getById(Integer id);

    void delete(Integer id);

}
