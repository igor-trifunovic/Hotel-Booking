package com.example.hotelbookingapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
@CrossOrigin(origins = "http://localhost:3000")
public class HelloController {
    @GetMapping
    public String hello() {
        return "Hello from Spring Boot!";
    }
}