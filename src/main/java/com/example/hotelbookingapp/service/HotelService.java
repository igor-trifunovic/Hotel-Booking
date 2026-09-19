package com.example.hotelbookingapp.service;

import com.example.hotelbookingapp.dto.CreateHotelRequest;
import com.example.hotelbookingapp.dto.HotelImageRequest;
import com.example.hotelbookingapp.dto.HotelSuggestionResponse;
import com.example.hotelbookingapp.model.Hotel;
import com.example.hotelbookingapp.model.HotelImage;
import com.example.hotelbookingapp.repository.HotelRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel createHotel(CreateHotelRequest request) {
        Hotel hotel = new Hotel();
        hotel.setName(request.name());
        hotel.setLocation(request.location());
        hotel.setDescription(request.description());
        hotel.setImageURL(request.imageURL());

        if (request.images() != null) {
            for (int i = 0; i < request.images().size(); i++) {
                HotelImageRequest source = request.images().get(i);
                HotelImage image = new HotelImage();
                image.setImageURL(source.imageURL());
                image.setSortOrder(source.sortOrder() != null ? source.sortOrder() : i);
                image.setHotel(hotel);
                hotel.getImages().add(image);
            }
        }

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