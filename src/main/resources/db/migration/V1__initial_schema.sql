CREATE TABLE IF NOT EXISTS users (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100)  NOT NULL,
    birth_date DATE,
    email      VARCHAR(150)  NOT NULL UNIQUE,
    password   VARCHAR(255)  NOT NULL,
    role       VARCHAR(50)   NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(150)  NOT NULL,
    location    VARCHAR(150)  NOT NULL,
    description TEXT
);

CREATE INDEX idx_hotel_name     ON hotel (name);
CREATE INDEX idx_hotel_location ON hotel (location);

CREATE TABLE IF NOT EXISTS room (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_number VARCHAR(20)    NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    hotel_id    BIGINT         NOT NULL,
    CONSTRAINT fk_room_hotel        FOREIGN KEY (hotel_id) REFERENCES hotel (id),
    CONSTRAINT uq_room_hotel_number UNIQUE (hotel_id, room_number)
);

CREATE TABLE IF NOT EXISTS reservation (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    check_in_date      DATE           NOT NULL,
    check_out_date     DATE           NOT NULL,
    total_price        DECIMAL(10, 2) NOT NULL,
    reservation_status VARCHAR(50)    NOT NULL DEFAULT 'CREATED',
    date_created       DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    room_id            BIGINT         NOT NULL,
    user_id            BIGINT         NOT NULL,
    CONSTRAINT fk_reservation_room FOREIGN KEY (room_id) REFERENCES room (id),
    CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX idx_reservation_availability ON reservation (room_id, check_in_date, check_out_date);
CREATE INDEX idx_reservation_user         ON reservation (user_id);
