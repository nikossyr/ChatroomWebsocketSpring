package com.example.chatroom.services;

import com.example.chatroom.model.User;

import java.util.Optional;


public interface UserServiceInterface {

    public Optional<User> getUserByUsername(String username);
}
