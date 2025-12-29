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
            Long hotelId, LocalDate checkInDate, LocalDate checkOutDate) {
        List<Room> rooms = roomRepository.findByHotelId(hotelId);

        return rooms.stream()
                    .filter(room -> reservationRepository
                    .findOverlappingReservations(
                            room.getId(), checkInDate, checkOutDate)
                    .isEmpty())
                    .toList();
    }

}