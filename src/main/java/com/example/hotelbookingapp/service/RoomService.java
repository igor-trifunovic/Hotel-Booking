package com.example.hotelbookingapp.service;

import com.example.hotelbookingapp.dto.CreateRoomRequest;
import com.example.hotelbookingapp.model.Hotel;
import com.example.hotelbookingapp.model.Room;
import com.example.hotelbookingapp.repository.HotelRepository;
import com.example.hotelbookingapp.repository.RoomRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    public List<Room> getRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    public Room createRoom(CreateRoomRequest request) {
        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new NoSuchElementException("Hotel not found."));

        Room room = new Room();
        room.setRoomNumber(request.roomNumber());
        room.setRoomType(request.roomType());
        room.setRoomPrice(request.price());

        room.setHotel(hotel);

        return roomRepository.save(room);
    }

}