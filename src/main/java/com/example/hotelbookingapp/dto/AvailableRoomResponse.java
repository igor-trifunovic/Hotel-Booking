package com.example.hotelbookingapp.dto;

import com.example.hotelbookingapp.enums.RoomType;
import com.example.hotelbookingapp.model.Room;

import java.math.BigDecimal;

public record AvailableRoomResponse(
    Long roomId,
    String roomNumber,
    RoomType roomType,
    BigDecimal price
) {
    public static AvailableRoomResponse from(Room room) {
        return new AvailableRoomResponse(
            room.getId(),
            room.getRoomNumber(),
            room.getRoomType(),
            room.getRoomPrice()
        );
    }
}