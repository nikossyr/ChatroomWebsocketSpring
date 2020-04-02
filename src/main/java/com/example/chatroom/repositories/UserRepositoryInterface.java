package com.example.chatroom.repositories;

import com.example.chatroom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryInterface extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);
}
