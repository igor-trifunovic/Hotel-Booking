package com.example.hotelbookingapp.controller;

import com.example.hotelbookingapp.dto.HotelSearchResponse;
import com.example.hotelbookingapp.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public List<HotelSearchResponse> search(
            @RequestParam String query,
            @RequestParam LocalDate checkIn,
            @RequestParam LocalDate checkOut
    ) {
        return searchService.search(query, checkIn, checkOut);
    }

}