package com.example.hotelbookingapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record HotelImageRequest(
   @NotBlank @Size(max = 512)
   @Pattern(regexp = "^(https?://|/).+",
            message = "Image URL must be an http(s) URL or start with /")
   String imageURL,

   Integer sortOrder
) {}