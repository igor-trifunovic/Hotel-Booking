package com.example.hotelbookingapp.dto;

public record CreateRoomRequest(

    Long hotelId,
    String roomNumber,
    String roomType,
    double roomPrice

) {}