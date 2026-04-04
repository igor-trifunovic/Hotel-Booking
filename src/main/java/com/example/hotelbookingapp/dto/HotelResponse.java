package com.example.hotelbookingapp.dto;

import com.example.hotelbookingapp.model.Hotel;

import java.util.List;

public record HotelResponse(
    Long id,
    String name,
    String location,
    String description,
    List<RoomResponse> rooms
) {
    public static HotelResponse from(Hotel hotel) {
        return new HotelResponse(
            hotel.getId(),
            hotel.getName(),
            hotel.getLocation(),
            hotel.getDescription(),
            hotel.getRooms().stream().map(RoomResponse::from).toList()
        );
    }
}