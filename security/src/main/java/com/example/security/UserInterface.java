package com.example.security;

import com.example.security.dtos.UserRequestDTO;
import com.example.security.dtos.UserResponseDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("/security")
public interface UserInterface {
    @RequestMapping("/searchUser")
    UserResponseDTO searchUser(@RequestBody UserRequestDTO requestDTO);

    @RequestMapping("/updateUser")
    void updateUser(@RequestBody UserResponseDTO responseDTO);
}
