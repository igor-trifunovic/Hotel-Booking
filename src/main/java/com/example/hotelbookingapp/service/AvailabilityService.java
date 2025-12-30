package com.example.hotelbookingapp.service;

import com.example.hotelbookingapp.model.Room;
import com.example.hotelbookingapp.repository.ReservationRepository;
import com.example.hotelbookingapp.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public List<Room> getAvailableRooms(
            Long hotelId, LocalDate checkIn, LocalDate checkOut) {
        List<Room> rooms = roomRepository.findByHotelId(hotelId);

        // Checks if there are overlapping reservations
        return rooms.stream()
                    .filter(room -> !reservationRepository
                    .existsConflictingReservation(room.getId(), checkIn, checkOut))
                    .toList();
    }

}