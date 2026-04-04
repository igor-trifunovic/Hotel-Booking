package com.example.hotelbookingapp.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
public class ReservationRequest {

    private Long roomId;

    @NotNull(message = "Check-in date is required.")
    @Future(message = "Check-in date must be in the future.")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required.")
    @Future(message = "Check-out date must be in the future.")
    private LocalDate checkOutDate;

}
