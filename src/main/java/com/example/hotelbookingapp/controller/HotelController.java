package com.example.hotelbookingapp.controller;

import com.example.hotelbookingapp.dto.CreateHotelRequest;
import com.example.hotelbookingapp.dto.HotelResponse;
import com.example.hotelbookingapp.dto.HotelSuggestionResponse;
import com.example.hotelbookingapp.model.Hotel;
import com.example.hotelbookingapp.service.HotelService;
import jakarta.validation.Valid;
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
    public HotelResponse createNewHotel(@RequestBody @Valid CreateHotelRequest request) {
        return HotelResponse.from(hotelService.createHotel(request));
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