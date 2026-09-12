package com.example.hotelbookingapp.controller;

import com.example.hotelbookingapp.dto.HotelResponse;
import com.example.hotelbookingapp.dto.HotelSuggestionResponse;
import com.example.hotelbookingapp.model.Hotel;
import com.example.hotelbookingapp.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public HotelResponse createNewHotel(@RequestBody Hotel hotel) {
        return HotelResponse.from(hotelService.saveHotel(hotel));
    }

    @GetMapping
    public List<HotelResponse> getAllHotels() {
        return hotelService.getAllHotels()
            .stream()
            .map(HotelResponse::from)
            .toList();
    }

    @GetMapping("/{id}")
    public HotelResponse getHotel(@PathVariable Long id) {
        return HotelResponse.from(hotelService.getById(id));
    }

    @GetMapping("/suggestions")
    public ResponseEntity<List<HotelSuggestionResponse>> getSuggestions(@RequestParam String query) {
        return ResponseEntity.ok(hotelService.getSuggestions(query));
    }
}