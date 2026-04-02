package com.example.hotelbookingapp.repository;

import com.example.hotelbookingapp.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByNameOrLocation(String name, String location);
}