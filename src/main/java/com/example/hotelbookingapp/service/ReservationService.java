package com.example.hotelbookingapp.service;

import com.example.hotelbookingapp.model.Reservation;
import com.example.hotelbookingapp.model.Room;
import com.example.hotelbookingapp.repository.ReservationRepository;
import com.example.hotelbookingapp.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    // Creates a new reservation
    public Reservation createReservation
            (Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate.isAfter(checkOutDate) || checkInDate.equals(checkOutDate)) {
            throw new IllegalArgumentException("Invalid date range.");
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found."));

        checkOverlapping(roomId, checkInDate, checkOutDate);

        long numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        double totalReservationPrice = numberOfNights * room.getPrice();

        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);
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

    //Deletes reservation
    public void deleteReservation(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new RuntimeException("Reservation does not exist.");
        }

        reservationRepository.deleteById(reservationId);
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