package com.example.hotelbookingapp.controller;

import com.example.hotelbookingapp.dto.CreateRoomRequest;
import com.example.hotelbookingapp.model.Room;
import com.example.hotelbookingapp.service.RoomService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public Room createRoom(@RequestBody CreateRoomRequest request) {
        return roomService.createRoom(request);
    }

    @GetMapping
    public List<Room> getRooms(@RequestParam Long hotelId) {
        return roomService.getRoomsByHotel(hotelId);
    }

}