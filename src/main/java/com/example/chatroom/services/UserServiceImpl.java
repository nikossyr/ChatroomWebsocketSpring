package com.example.chatroom.services;

import com.example.chatroom.model.User;
import com.example.chatroom.repositories.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceInterface {

    @Autowired
    UserRepositoryInterface userRepository;

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
