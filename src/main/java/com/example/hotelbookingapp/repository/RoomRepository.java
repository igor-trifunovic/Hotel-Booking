package com.example.hotelbookingapp.repository;

import com.example.hotelbookingapp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotelId(Long hotelId);

    @Query("""
        SELECT r FROM Room r
        WHERE r.hotel.id IN :hotelIds
        AND NOT EXISTS (
            SELECT res FROM Reservation res
            WHERE res.room = r
            AND res.checkOutDate > :checkIn
            AND res.checkInDate < :checkOut
        )
    """)

    List<Room> findAvailableRoomsForHotels(
        @Param("hotelIds") List<Long> hotelIds,
        @Param("checkIn")LocalDate checkIn,
        @Param("checkOut") LocalDate checkOut
    );

}