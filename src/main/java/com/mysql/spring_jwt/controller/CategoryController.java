package com.mysql.spring_jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.spring_jwt.model.Category;
import com.mysql.spring_jwt.service.table.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity
            .status(200)
            .body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity
            .status(200)
            .body(service.getById(id));
    }

    @PostMapping("/store")
    public ResponseEntity<?> store(@RequestBody Category request) {
        return ResponseEntity
            .status(201)
            .body(service.store(request));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Category request) {
        return ResponseEntity
            .status(200)
            .body(service.update(id, request));
    }

    @DeleteMapping("/{id}/destroy")
    public ResponseEntity<?> destroy(@PathVariable Integer id) {
        return ResponseEntity
            .status(204)
            .body(service.destroy(id)
        );
    }

}
