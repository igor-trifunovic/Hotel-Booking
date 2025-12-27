package com.example.hotelbookingapp.repository;

import com.example.hotelbookingapp.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("""
        SELECT r from Reservation r
        WHERE r.room.id = :roomId
        AND r.checkOutDate > :checkIn
        AND r.checkInDate < :checkOut
    """)
    List<Reservation> findOverlappingReservations(
        @Param("roomId") Long roomId,
        @Param("checkIn")LocalDate checkIn,
        @Param("checkOut")LocalDate checkOut
    );
}