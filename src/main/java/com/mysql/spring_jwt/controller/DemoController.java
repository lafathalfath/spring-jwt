package com.mysql.spring_jwt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/demo")
public class DemoController {
    
    @GetMapping
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello form secret url");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("Hello admin");
    }
    
}
