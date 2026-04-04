package com.example.hotelbookingapp.service;

import com.example.hotelbookingapp.dto.HotelSearchResponse;
import com.example.hotelbookingapp.model.Hotel;
import com.example.hotelbookingapp.model.Room;
import com.example.hotelbookingapp.repository.HotelRepository;
import com.example.hotelbookingapp.repository.ReservationRepository;
import com.example.hotelbookingapp.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public List<HotelSearchResponse> search(
            String query,
            LocalDate checkIn,
            LocalDate checkOut
    ) {
        List<Hotel> hotels = hotelRepository.findByNameOrLocation(query, query);

        return hotels.stream().map(hotel -> {
            List<Room> rooms = roomRepository.findByHotelId(hotel.getId());
            List<Room> availableRooms = rooms.stream().
                    filter(room -> isRoomAvailable(room.getId(), checkIn, checkOut))
                    .toList();

            double minPrice = availableRooms.stream()
                    .mapToDouble(Room::getPrice)
                    .min()
                    .orElse(0);

            return new HotelSearchResponse(
                    hotel.getId(),
                    hotel.getName(),
                    hotel.getLocation(),
                    availableRooms.size(),
                    minPrice
            );
        }).toList();
    }

    public boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        return !reservationRepository
               .existsConflictingReservation(roomId, checkIn, checkOut);
    }

}