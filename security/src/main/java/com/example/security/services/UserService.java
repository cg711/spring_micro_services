package com.example.security.services;

import com.example.security.dtos.UserRequestDTO;
import com.example.security.dtos.UserResponseDTO;
import com.example.security.model.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    public UserResponseDTO searchUser(UserRequestDTO requestDTO);
    public AppUser updateUser(UserResponseDTO requestDTO);


    }
