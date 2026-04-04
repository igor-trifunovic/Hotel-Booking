package com.example.hotelbookingapp.dto;

import com.example.hotelbookingapp.enums.ReservationStatus;
import com.example.hotelbookingapp.model.Reservation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationResponse(
    Long id,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    BigDecimal totalPrice,
    ReservationStatus status,
    LocalDateTime dateCreated,
    Long roomId,
    String roomNumber,
    Long userId,
    String username
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getCheckInDate(),
            reservation.getCheckOutDate(),
            reservation.getTotalPrice(),
            reservation.getReservationStatus(),
            reservation.getDateCreated(),
            reservation.getRoom().getId(),
            reservation.getRoom().getRoomNumber(),
            reservation.getUser().getId(),
            reservation.getUser().getName()
        );
    }
}
