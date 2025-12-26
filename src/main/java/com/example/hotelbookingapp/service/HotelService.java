package com.example.hotelbookingapp.service;

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

}
