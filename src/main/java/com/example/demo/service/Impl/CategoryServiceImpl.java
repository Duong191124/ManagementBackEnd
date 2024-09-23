package com.example.demo.service.Impl;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.models.Category;
import com.example.demo.repository.CategoryRepo;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category save(CategoryDTO categoryDTO) {
        try {
            return categoryRepo.save(
                    Category
                            .builder()
                            .name(categoryDTO.getName())
                            .build()
            );
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Category " + categoryDTO.getName() + " is already taken", e );
        }
    }

    @Override
    public Category update(Integer id, CategoryDTO categoryDTO) {
        Category existingCategory = getById(id);
        existingCategory.setName(categoryDTO.getName());
        return categoryRepo.save(existingCategory);
    }

    @Override
    public Category getById(Integer id) {
        return categoryRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        Category existingCategory = getById(id);
        categoryRepo.delete(existingCategory);
    }
}
