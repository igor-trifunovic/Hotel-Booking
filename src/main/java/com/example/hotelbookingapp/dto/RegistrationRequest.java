package com.example.hotelbookingapp.dto;

public record RegistrationRequest(

        String name,
        String email,
        String password

) {}