package com.example.hotelbookingapp.dto;

import com.example.hotelbookingapp.model.HotelImage;

public record HotelImageResponse(
    Long id,
    String imageURL,
    int sortOrder
) {
    public static HotelImageResponse from(HotelImage image) {
        return new HotelImageResponse(
            image.getId(),
            image.getImageURL(),
            image.getSortOrder()
        );
    }
}
