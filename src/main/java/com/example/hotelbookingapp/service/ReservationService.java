package com.example.hotelbookingapp.service;

import com.example.hotelbookingapp.enums.ReservationStatus;
import com.example.hotelbookingapp.model.*;
import com.example.hotelbookingapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    // Creates a new reservation
    public Reservation createReservation
            (Long userId, Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        if (reservationRepository.existsConflictingReservation(roomId, checkInDate, checkOutDate)) {
            throw new IllegalArgumentException("Invalid date range.");
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        checkOverlapping(roomId, checkInDate, checkOutDate);

        long numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        double totalReservationPrice = numberOfNights * room.getPrice();

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);
        reservation.setReservationStatus(ReservationStatus.CREATED);
        reservation.setDateCreated(LocalDateTime.now());
        reservation.setTotalPrice(totalReservationPrice);

        return reservationRepository.save(reservation);
    }

    // Gets all reservations that belong to one hotel
    public List<Reservation> getReservationsByHotel(Long hotelId) {
        return reservationRepository.findByRoomHotelId(hotelId);
    }

    // Updates reservation
    public Reservation updateReservation(Long reservationId, LocalDate checkInDate, LocalDate checkOutDate) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found."));

        long numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        if (numberOfNights <= 0) throw new IllegalArgumentException("Invalid date range.");

        double newTotalReservationPrice = numberOfNights * reservation.getRoom().getPrice();

        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);
        reservation.setTotalPrice(newTotalReservationPrice);

        return reservationRepository.save(reservation);
    }

    //Cancels reservation
    public void cancelReservation(Long reservationId, Long userId) {
        Reservation reservation = reservationRepository
                    .findById(reservationId)
                    .orElseThrow();

        if (!reservation.getUser().getId().equals(userId)) {
            throw new SecurityException("Forbidden");
        }

        reservation.setReservationStatus(ReservationStatus.CANCELLED);
    }

    // Checks if there are any reservations for the same period of time
    public void checkOverlapping(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        boolean exists = !reservationRepository.existsConflictingReservation
                (roomId, checkIn, checkOut);

        if (exists) {
            throw new RuntimeException("Room is already booked for selected period.");
        }
    }

}