package com.example.hotelbookingapp.controller;

import com.example.hotelbookingapp.dto.AvailableRoomResponse;
import com.example.hotelbookingapp.service.AvailabilityService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping
    public List<AvailableRoomResponse> getAvailableRoomsForHotel(
            @RequestParam Long hotelId,
            @RequestParam LocalDate checkIn,
            @RequestParam LocalDate checkOut
            ) {
        return availabilityService
            .getAvailableRooms(hotelId, checkIn, checkOut)
            .stream()
            .map(AvailableRoomResponse::from)
            .toList();
    }

}