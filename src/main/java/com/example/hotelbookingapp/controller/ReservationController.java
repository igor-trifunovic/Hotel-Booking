package com.example.hotelbookingapp.controller;

import com.example.hotelbookingapp.dto.ReservationRequest;
import com.example.hotelbookingapp.model.Reservation;
import com.example.hotelbookingapp.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.createReservation(
            request.getRoomId(),
            request.getCheckInDate(),
            request.getCheckOutDate()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    @GetMapping
    public List<Reservation> getReservationsByHotel(@RequestParam Long hotelId) {
        return reservationService.getReservationsByHotel(hotelId);
    }
}