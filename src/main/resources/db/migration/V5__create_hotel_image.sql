-- Gallery images for a hotel. hotel.image_url stays the cover image shown on
-- search cards; these are the additional photos shown on the hotel page.
CREATE TABLE IF NOT EXISTS hotel_image (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    hotel_id   BIGINT       NOT NULL,
    image_url  VARCHAR(512) NOT NULL,
    sort_order INT          NOT NULL DEFAULT 0,
    -- Declared before the FK so MySQL reuses it instead of adding its own hotel_id index.
    INDEX idx_hotel_image_hotel (hotel_id, sort_order),
    CONSTRAINT fk_hotel_image_hotel FOREIGN KEY (hotel_id) REFERENCES hotel_image (hotel_id) ON DELETE CASCADE
);