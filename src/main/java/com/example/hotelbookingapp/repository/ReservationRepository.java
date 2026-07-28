package com.example.hotelbookingapp.repository;

import com.example.hotelbookingapp.enums.ReservationStatus;
import com.example.hotelbookingapp.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
        SELECT COUNT(r) > 0
        from Reservation r
        WHERE r.room.id = :roomId
        AND r.reservationStatus != :cancelledStatus
        AND r.checkOutDate > :checkIn
        AND r.checkInDate < :checkOut
    """)

    boolean existsConflictingReservation(
        @Param("roomId") Long roomId,
        @Param("checkIn") LocalDate checkIn,
        @Param("checkOut") LocalDate checkOut,
        @Param("cancelledStatus") ReservationStatus cancelledStatus
    );

    @Query("""
        SELECT COUNT(r) > 0
        FROM Reservation r
        WHERE r.room.id = :roomId
        AND r.reservationStatus != :cancelledStatus
        AND r.id != :excludeId
        AND r.checkOutDate > :checkIn
        AND r.checkInDate < :checkOut
    """)
    boolean existsConflictingReservationExcludingSelf(
        @Param("roomId") Long roomId,
        @Param("checkIn") LocalDate checkIn,
        @Param("checkOut") LocalDate checkOut,
        @Param("cancelledStatus") ReservationStatus cancelledStatus,
        @Param("excludeId") Long excludeId
    );

    List<Reservation> findByRoomHotelId(Long hotelId);

    List<Reservation> findByUserEmail(String email);

}