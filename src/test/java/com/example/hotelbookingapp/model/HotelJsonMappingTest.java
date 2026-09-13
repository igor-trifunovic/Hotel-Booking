package com.example.hotelbookingapp.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// POST /api/hotels binds the Hotel entity directly, so Jackson must handle both
// managed collections (rooms and images) and wire each child back to its hotel.
class HotelJsonMappingTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deserializesRoomsAndImagesWithBackReferences() throws Exception {
        String json = """
            {
              "name": "Hotel Moskva",
              "location": "Belgrade",
              "imageURL": "https://cdn.example.com/moskva/cover.jpg",
              "rooms":  [ { "roomNumber": "101", "roomType": "SINGLE_BED", "roomPrice": 80 } ],
              "images": [ { "imageURL": "https://cdn.example.com/moskva/lobby.jpg", "sortOrder": 0 },
                          { "imageURL": "https://cdn.example.com/moskva/pool.jpg",  "sortOrder": 1 } ]
            }
            """;

        Hotel hotel = objectMapper.readValue(json, Hotel.class);

        assertThat(hotel.getImageURL()).isEqualTo("https://cdn.example.com/moskva/cover.jpg");
        assertThat(hotel.getRooms()).singleElement()
                .satisfies(room -> assertThat(room.getHotel()).isSameAs(hotel));
        assertThat(hotel.getImages()).hasSize(2)
                .allSatisfy(image -> assertThat(image.getHotel()).isSameAs(hotel));
        assertThat(hotel.getImages()).extracting(HotelImage::getSortOrder).containsExactly(0, 1);
    }
}
