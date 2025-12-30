package com.example.hotelbookingapp.controller;

import com.example.hotelbookingapp.model.Room;
import com.example.hotelbookingapp.service.AvailabilityService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/availability")
@CrossOrigin(origins = "http://localhost:3000")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
        System.out.println("AvailabilityController LOADED");
    }

    @GetMapping
    public List<Long> getAvailableRoomsForHotel(
            @RequestParam Long hotelId,
            @RequestParam LocalDate checkIn,
            @RequestParam LocalDate checkOut
    ) {
        return availabilityService
                .getAvailableRooms(hotelId, checkIn, checkOut)
                .stream()
                .map(Room::getId)
                .toList();
    }

}