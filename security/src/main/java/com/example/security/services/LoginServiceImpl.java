package com.example.security.services;

import com.example.security.dtos.UserResponseDTO;
import com.example.security.jwt.JwtUtil;
import com.example.security.dtos.LoginRequestDTO;
import com.example.security.dtos.UserRequestDTO;
import com.example.security.repository.SecurityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    SecurityRepository securityRepository;

    @Autowired
    private UserService userService;

    /**
     * Attempts to login the user given a username and password.
     * @param loginRequestDTO JSON body storing credential and password
     * @param request Serverlet request object
     * @return Created JWT token.
     */
    @Override
    public String login(LoginRequestDTO loginRequestDTO, HttpServletRequest request) {
        UserResponseDTO user = fetchUserDetails.apply(loginRequestDTO);
        validateUserUsername.accept(user);
        validateUserPassword.accept(loginRequestDTO, user);
        return jwtUtil.createJWT(loginRequestDTO.getUsername(), request);
    }

    /**
     * Returns the details of a specified user.
     */
    private Function<LoginRequestDTO, UserResponseDTO> fetchUserDetails = (loginRequestDTO) -> {
        return userService.searchUser(UserRequestDTO.builder().username(loginRequestDTO.getUsername()).build());
    };

    /**
     * Attempts to validate a username.
     */
    private Consumer<UserResponseDTO> validateUserUsername = (user) -> {
        if (Objects.isNull(user)) {
            throw new RuntimeException();
        }
        LOGGER.info("Username validated...");
    };
    /**
     * Attempts to validate a password.
     */
    private BiConsumer<LoginRequestDTO, UserResponseDTO> validateUserPassword = (requestDTO, user) -> {
        if (BCrypt.checkpw(requestDTO.getPassword(), user.getPassword())) {
            userService.updateUser(user);
        } else {
            LOGGER.debug("Incorrect password...");
        }
        LOGGER.info("Password validated...");
    };

}
