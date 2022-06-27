package com.example.security;

import com.example.security.model.User;
import com.example.security.repository.SecurityRepository;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ApplicationStartUp {
    @Autowired
    private SecurityRepository securityRepository;

    /**
     * Initilaizes an admin user on startup.
     * @return
     */
    @Bean
    public CommandLineRunner loadData() {
        return (args) -> {
            List<User> users = securityRepository.findAll();
            if(users.isEmpty()) {
                securityRepository.save(User.builder()
                        .username("admin")
                        .password(BCrypt.hashpw("password", BCrypt.gensalt()))
                        .build());
            }
        };
    }
}
