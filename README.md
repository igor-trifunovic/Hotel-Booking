# Hotel Booking API

A REST backend for hotel reservations, built with Spring Boot 3.5 and Java 21 on MySQL.

> Frontend: [Hotel-Booking-Frontend](https://github.com/igor-trifunovic/Hotel-Booking-Frontend)

Guests search hotels by name or location for a date range and see only the rooms
that are genuinely free, then book, review and cancel their reservations. Booking
conflicts are resolved in the database with a date-overlap query rather than in
application code, and the schema is versioned with Flyway instead of Hibernate
auto-DDL, so every change is reviewable and reproducible.

This repository holds the API only. The React client lives in
[Hotel-Booking-Frontend](https://github.com/igor-trifunovic/Hotel-Booking-Frontend)
and talks to it over REST; CORS here is configured for that client on
`localhost:3000`.
