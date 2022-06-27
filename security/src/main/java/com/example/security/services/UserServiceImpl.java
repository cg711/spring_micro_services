package com.example.security.services;

import com.example.security.model.AppUser;
import com.example.security.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserDetailsService {

    @Autowired
    SecurityRepository securityRepository;

//    List<GrantedAuthority> grantedAuthorities = user.getRoles()
//            .stream().map(SimpleGrantedAuthority::new)
//            .collect(Collectors.toList());

    //TODO: add roles
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = securityRepository.fetchUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new User(username, user.getPassword(), true, true, true, true, null);
    }
}
