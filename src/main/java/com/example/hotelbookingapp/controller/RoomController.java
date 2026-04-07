package com.example.hotelbookingapp.controller;

import com.example.hotelbookingapp.dto.CreateRoomRequest;
import com.example.hotelbookingapp.dto.RoomResponse;
import com.example.hotelbookingapp.model.Room;
import com.example.hotelbookingapp.service.RoomService;
import jakarta.validation.Valid;
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
    public RoomResponse createRoom(@RequestBody @Valid CreateRoomRequest request) {
        return RoomResponse.from(roomService.createRoom(request));
    }

    @GetMapping
    public List<RoomResponse> getRooms(@RequestParam Long hotelId) {
        return roomService.getRoomsByHotel(hotelId).stream()
                .map(RoomResponse::from)
                .toList();
    };

}