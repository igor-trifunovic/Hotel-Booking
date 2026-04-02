package com.example.hotelbookingapp.dto;

public record HotelSearchResponse(
    Long id,
    String name,
    String location,
    int availableRooms,
    double minPrice
) {}
