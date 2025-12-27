package com.example.hotelbookingapp.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
public class ReservationRequest {

    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

}
