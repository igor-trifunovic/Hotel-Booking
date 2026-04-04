package com.example.hotelbookingapp.dto;

import com.example.hotelbookingapp.model.Room;

import java.math.BigDecimal;

public record RoomResponse(
    Long id,
    String roomNumber,
    BigDecimal price,
    Long hotelId
) {
    public static RoomResponse from(Room room) {
        return new RoomResponse(
            room.getId(),
            room.getRoomNumber(),
            room.getPrice(),
            room.getHotel().getId()
        );
    }
}