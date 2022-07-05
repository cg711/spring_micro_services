package com.example.security.services;

import com.example.security.dtos.LoginRequestDTO;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public interface LoginService {
    public String login(LoginRequestDTO loginRequestDTO, HttpServletRequest request);
}
