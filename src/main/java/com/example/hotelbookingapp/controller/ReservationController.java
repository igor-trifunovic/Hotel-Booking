package com.example.hotelbookingapp.controller;

import com.example.hotelbookingapp.dto.ReservationRequest;
import com.example.hotelbookingapp.model.Reservation;
import com.example.hotelbookingapp.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public Reservation createReservation(@RequestBody @Valid ReservationRequest request) {
        return reservationService.createReservation(request);
    }

    @GetMapping
    public List<Reservation> getReservationsByHotel(@RequestParam Long hotelId) {
        return reservationService.getReservationsByHotel(hotelId);
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody @Valid ReservationRequest request) {
        return reservationService.updateReservation
                (id, request.getCheckInDate(), request.getCheckOutDate());
    }

    @DeleteMapping("/{id}")
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }

    @GetMapping("/me")
    public List<Reservation> getMyReservations() {
        return reservationService.getMyReservations();
    }

}