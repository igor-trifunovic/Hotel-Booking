package com.example.hotelbookingapp.controller;

import com.example.hotelbookingapp.model.Room;
import com.example.hotelbookingapp.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @GetMapping
    public List<Room> checkRoomAvailability
            (@RequestParam Long hotelId, @RequestParam LocalDate checkIn, @RequestParam LocalDate checkOut) {
        return availabilityService.getAvailableRooms(hotelId, checkIn, checkOut);
    }

}