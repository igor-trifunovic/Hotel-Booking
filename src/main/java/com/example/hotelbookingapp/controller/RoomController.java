package com.example.hotelbookingapp.controller;

import com.example.hotelbookingapp.model.Room;
import com.example.hotelbookingapp.service.RoomService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:3000")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomService.save(room);
    }

    @GetMapping
    public List<Room> getRooms() {
        return roomService.getAllRooms();
    }

}
