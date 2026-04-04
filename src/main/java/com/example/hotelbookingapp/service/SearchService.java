package com.example.hotelbookingapp.service;

import com.example.hotelbookingapp.dto.HotelSearchResponse;
import com.example.hotelbookingapp.model.Hotel;
import com.example.hotelbookingapp.model.Room;
import com.example.hotelbookingapp.repository.HotelRepository;
import com.example.hotelbookingapp.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public List<HotelSearchResponse> search(
            String query,
            LocalDate checkIn,
            LocalDate checkOut
    ) {
        // Query to find matching hotels
        List<Hotel> hotels = hotelRepository.findByNameOrLocation(query, query);
        if (hotels.isEmpty()) return List.of();

        List<Long> hotelIDs = hotels.stream().map(Hotel::getId).toList();

        // Query to find all available rooms for all matching hotels
        Map<Long, List<Room>> availableRoomsByHotel = roomRepository
                .findAvailableRoomsForHotels(hotelIDs, checkIn, checkOut)
                .stream()
                .collect(Collectors.groupingBy(room -> room.getHotel().getId()));

        return hotels.stream().map(hotel -> {
            List<Room> availableRooms = availableRoomsByHotel
                    .getOrDefault(hotel.getId(), List.of());

            BigDecimal minPrice = availableRooms.stream()
                    .map(Room::getPrice)
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);

            return new HotelSearchResponse(
                    hotel.getId(),
                    hotel.getName(),
                    hotel.getLocation(),
                    availableRooms.size(),
                    minPrice
            );
        }).toList();
    }

}