package com.example.security.services;

import com.example.security.dtos.LoginRequestDTO;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    public String login(LoginRequestDTO loginRequestDTO, HttpServletRequest request);
}
