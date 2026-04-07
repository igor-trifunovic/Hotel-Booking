package com.example.hotelbookingapp.dto;

import com.example.hotelbookingapp.enums.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateRoomRequest(

    @NotNull(message = "Hotel ID is required")
    Long hotelId,

    @NotBlank(message = "Room number is required")
    String roomNumber,

    @NotNull(message = "Room type is required")
    RoomType roomType,

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    BigDecimal price

) {}