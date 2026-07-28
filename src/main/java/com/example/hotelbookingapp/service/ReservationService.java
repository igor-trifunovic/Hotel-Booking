package com.example.hotelbookingapp.service;

import com.example.hotelbookingapp.dto.ReservationRequest;
import com.example.hotelbookingapp.enums.ReservationStatus;
import com.example.hotelbookingapp.model.*;
import com.example.hotelbookingapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    // Create a new reservation
    @Transactional
    public Reservation createReservation(ReservationRequest request) {
        if(reservationRepository.existsConflictingReservation(
            request.getRoomId(),
            request.getCheckInDate(),
            request.getCheckOutDate(),
            ReservationStatus.CANCELLED
        )) {
            throw new RuntimeException("Room already booked.");
        }

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found."));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        long numberOfNights = ChronoUnit.DAYS.between(
                request.getCheckInDate(), request.getCheckOutDate());

        if (numberOfNights <= 0)
            throw new IllegalArgumentException("Check-out date must be after check-in date.");

        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setUser(user);
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());
        reservation.setReservationStatus(ReservationStatus.CREATED);
        reservation.setDateCreated(LocalDateTime.now());
        reservation.setTotalPrice(room.getRoomPrice().multiply(BigDecimal.valueOf(numberOfNights)));

        return reservationRepository.save(reservation);
    }

    // Update reservation
    @Transactional
    public Reservation updateReservation(
            Long reservationId, LocalDate checkInDate, LocalDate checkOutDate) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found."));

        verifyOwnership(reservation);

        long numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        if (numberOfNights <= 0) throw new IllegalArgumentException("Invalid date range.");

        // Conflict check: ensure the new dates don't overlap another active reservation
        if (reservationRepository.existsConflictingReservationExcludingSelf(
                reservation.getRoom().getId(), checkInDate, checkOutDate,
                ReservationStatus.CANCELLED, reservationId)) {
            throw new IllegalArgumentException("Room is already booked for the selected dates.");
        }

        BigDecimal newTotalReservationPrice = reservation.getRoom().getRoomPrice()
                .multiply(BigDecimal.valueOf(numberOfNights));

        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);
        reservation.setTotalPrice(newTotalReservationPrice);

        return reservationRepository.save(reservation);
    }

    //Cancel reservation
    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                    .orElseThrow(() -> new RuntimeException("Reservation not found."));

        verifyOwnership(reservation);

        reservation.setReservationStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    // Verifies that the currently authenticated user owns the reservation
    private void verifyOwnership(Reservation reservation) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        if (!reservation.getUser().getEmail().equals(email)) {
            throw new SecurityException("Forbidden");
        }
    }

    // Get all reservations for the specific hotel
    public List<Reservation> getReservationsByHotel(Long hotelId) {
        return reservationRepository.findByRoomHotelId(hotelId);
    }

    // Get all reservations that belong to specific user
    public List<Reservation> getMyReservations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return reservationRepository.findByUserEmail(email);
    }

}