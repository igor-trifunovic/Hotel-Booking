package com.example.hotelbookingapp.controller;

import com.example.hotelbookingapp.dto.ReservationRequest;
import com.example.hotelbookingapp.dto.ReservationResponse;
import com.example.hotelbookingapp.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ReservationResponse createReservation(@RequestBody @Valid ReservationRequest request) {
        return ReservationResponse.from(reservationService.createReservation(request));
    }

    @GetMapping
    public List<ReservationResponse> getReservationsByHotel(@RequestParam Long hotelId) {
        return reservationService.getReservationsByHotel(hotelId).stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PutMapping("/{id}")
    public ReservationResponse updateReservation(@PathVariable Long id, @RequestBody @Valid ReservationRequest request) {
        return ReservationResponse.from(reservationService.updateReservation
                (id, request.getCheckInDate(), request.getCheckOutDate()));
    }

    @DeleteMapping("/{id}")
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }

    @GetMapping("/me")
    public List<ReservationResponse> getMyReservations() {
        return reservationService.getMyReservations().stream()
                .map(ReservationResponse::from)
                .toList();
    }

}