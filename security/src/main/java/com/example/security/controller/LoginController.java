package com.example.security.controller;

import com.example.security.dtos.LoginRequestDTO;
import com.example.security.services.LoginService;
import com.example.security.services.LoginServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.ResponseEntity.ok;

@AllArgsConstructor
@RestController
@RequestMapping("/security")
public class LoginController {
    private LoginService loginService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

    /**
     * Attempts to authenticate a user.
     * @param requestDTO JSON body with credential and password.
     * @param request Http incoming request.
     * @return New ResponseEntity object.
     */
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDTO requestDTO, HttpServletRequest request) {
        String token = loginService.login(requestDTO, request);
        LOGGER.info(token);
        return ok().body(loginService.login(requestDTO, request));
    }

}
