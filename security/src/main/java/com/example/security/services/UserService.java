package com.example.security.services;

import com.example.security.model.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    AppUser findByUserName(String username) throws UsernameNotFoundException;
}
