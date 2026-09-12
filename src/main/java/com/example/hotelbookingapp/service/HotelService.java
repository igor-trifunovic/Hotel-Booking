package com.example.hotelbookingapp.service;

import com.example.hotelbookingapp.dto.HotelSuggestionResponse;
import com.example.hotelbookingapp.model.Hotel;
import com.example.hotelbookingapp.repository.HotelRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel saveHotel(Hotel hotel) {
        hotel.getRooms().forEach(room -> room.setHotel(hotel));
        return hotelRepository.save(hotel);
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found."));
    }

    public List<HotelSuggestionResponse> getSuggestions(String query) {
        return hotelRepository
            .findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(query, query)
            .stream()
            .map(h -> new HotelSuggestionResponse(h.getId(), h.getName(), h.getLocation()))
            .toList();
    }

}