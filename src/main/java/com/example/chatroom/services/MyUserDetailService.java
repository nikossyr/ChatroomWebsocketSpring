package com.example.chatroom.services;

import com.example.chatroom.model.User;
import com.example.chatroom.model.UserCredentials;
import com.example.chatroom.repositories.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepositoryInterface userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsername(userName);
        userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found " + userName));
        return userOptional.map(UserCredentials::new).get();

    }
}
