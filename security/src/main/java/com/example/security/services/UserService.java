package com.example.security.services;

import com.example.security.dtos.UserRequestDTO;
import com.example.security.dtos.UserResponseDTO;
import com.example.security.model.AppUser;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

public interface UserService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    public UserResponseDTO searchUser(UserRequestDTO requestDTO);
    public AppUser updateUser(UserRequestDTO requestDTO);


    }
