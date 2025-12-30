package com.example.hotelbookingapp.repository;

import com.example.hotelbookingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
