package com.mysql.spring_jwt.service.table;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mysql.spring_jwt.dto.ResponseDto;
import com.mysql.spring_jwt.model.Category;
import com.mysql.spring_jwt.repository.CategoryRepository;

@Service
public class CategoryService {
    
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }
    
    public ResponseDto<List<Category>> getAll() {
        return new ResponseDto<List<Category>>(
            "OK", 
            "get all categories successfully", 
            repository.findAll()
        );
    }

    public ResponseDto<Category> getById(Integer id) {
        Category category = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
        return new ResponseDto<Category>(
            "OK", 
            "get category successfully", 
            category
        );
    }

    public ResponseDto<Category> store(Category request) {
        return new ResponseDto<Category>(
            "CREATED",
            "category created successfully",
            repository.save(request)
        );
    }

    public ResponseDto<Category> update(Integer id, Category request) {
        Category category = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
        category.setName(request.getName());
        Category updatedCategory = repository.save(category);
        return new ResponseDto<Category>(
            "UPDATED", 
            "category updated successfully", 
            updatedCategory
        );
    }

    public ResponseDto<Void> destroy(Integer id) {
        Category category = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
        repository.delete(category);
        return new ResponseDto<Void>(
            "DELETED", 
            "category deleted",
            null
        );
    } 

}
