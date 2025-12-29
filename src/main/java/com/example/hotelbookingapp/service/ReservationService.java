package com.example.hotelbookingapp.service;

import com.example.hotelbookingapp.model.Reservation;
import com.example.hotelbookingapp.model.Room;
import com.example.hotelbookingapp.repository.ReservationRepository;
import com.example.hotelbookingapp.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public Reservation createReservation
            (Long roomId, LocalDate checkIn, LocalDate checkOut) {
        if (checkIn.isAfter(checkOut) || checkIn.equals(checkOut)) {
            throw new IllegalArgumentException("Invalid date range.");
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found."));

        boolean exists = !reservationRepository.findOverlappingReservations
                (roomId, checkIn, checkOut).isEmpty();

        if (exists) {
            throw new RuntimeException("Room is already booked for selected period.");
        }

        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setCheckInDate(checkIn);
        reservation.setCheckOutDate(checkOut);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByHotel(Long hotelId) {
        return reservationRepository.findByRoomHotelId(hotelId);
    }
}