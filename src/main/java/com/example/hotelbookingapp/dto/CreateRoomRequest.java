package com.example.hotelbookingapp.dto;

import java.math.BigDecimal;

public record CreateRoomRequest(

    Long hotelId,
    String roomNumber,
    String roomType,
    BigDecimal roomPrice

) {}