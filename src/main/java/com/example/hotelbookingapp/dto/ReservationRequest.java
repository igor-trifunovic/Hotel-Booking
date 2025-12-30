package com.example.hotelbookingapp.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
public class ReservationRequest {

    private Long roomId;
    private Long userId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public record CreateReservationRequest(
         Long userId,
         Long roomId,
         LocalDate checkInDate,
         LocalDate checkOutDate
    ) {}

}
