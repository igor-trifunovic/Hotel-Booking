package com.example.hotelbookingapp.service;

import com.example.hotelbookingapp.dto.CreateRoomRequest;
import com.example.hotelbookingapp.model.Hotel;
import com.example.hotelbookingapp.model.Room;
import com.example.hotelbookingapp.repository.HotelRepository;
import com.example.hotelbookingapp.repository.RoomRepository;
import org.springframework.stereotype.Service;
import java.util.List;

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
        // Find hotel by hotelId
        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found."));

        // Create new room
        Room room = new Room();
        room.setRoomNumber(request.roomNumber());
        room.setRoomType(request.roomType());
        room.setRoomPrice(request.price());

        // Connect room with the hotel via JPA
        room.setHotel(hotel);

        // Save room to database
        return roomRepository.save(room);
    }

}