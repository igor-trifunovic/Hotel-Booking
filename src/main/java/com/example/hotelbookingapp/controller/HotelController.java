package com.example.hotelbookingapp.controller;

import com.example.hotelbookingapp.model.Hotel;
import com.example.hotelbookingapp.service.HotelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<Hotel> getHotels() {
        return hotelService.getAllHotels();
    }

    @PostMapping
    public Hotel createNewHotel(@RequestBody Hotel hotel) {
        return hotelService.addHotel(hotel);
    }
}
