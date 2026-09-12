package com.example.hotelbookingapp.dto;

import java.math.BigDecimal;

public record HotelSearchResponse(
    Long id,
    String name,
    String location,
    String description,
    String imageURL,
    int availableRooms,
    BigDecimal minPrice
) {}
