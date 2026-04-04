package com.example.hotelbookingapp.dto;

import java.math.BigDecimal;

public record HotelSearchResponse(
    Long id,
    String name,
    String location,
    int availableRooms,
    BigDecimal minPrice
) {}
