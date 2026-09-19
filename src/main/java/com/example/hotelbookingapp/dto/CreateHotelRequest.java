package com.example.hotelbookingapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateHotelRequest(
   @NotBlank(message = "Name is required.")
   @Size(max = 150) String name,

   @NotBlank(message = "Location is required.")
   @Size(max = 150) String location,

   String description,

   @Size(max = 512)
   @Pattern(regexp = "^(https?://|/).+",
            message = "Image URL must be an http(s) URL or start with /")
   String imageURL,

   List<@Valid HotelImageRequest> images
) {}