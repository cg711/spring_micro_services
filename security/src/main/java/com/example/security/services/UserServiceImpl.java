package com.example.security.services;

import com.example.security.dtos.UserRequestDTO;
import com.example.security.dtos.UserResponseDTO;
import com.example.security.model.AppUser;
import com.example.security.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    SecurityRepository securityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Returns UserDetails from provided username.
     * @param username Username of user.
     * @return New User object
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = securityRepository.fetchUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        List<GrantedAuthority> grantedAuthorities = user.getRoles()
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new User(username, user.getPassword(), true, true, true, true, grantedAuthorities);
    }

    public static Function<Object[], UserResponseDTO> convertToResponse = (object) -> {
        final Integer ADMIN_ID = 0;

        return UserResponseDTO.builder()
                .id(Long.parseLong(object[ADMIN_ID].toString()))
                .build();
    };

    public static Function<List<Object[]>, UserResponseDTO> convertToUserResponse = (objects) -> {
        final Integer ID = 0;
        final Integer PASSWORD = 1;

        UserResponseDTO responseDTO = new UserResponseDTO();

        objects.forEach(object -> {
            responseDTO.setId(Long.parseLong(object[ID].toString()));
            responseDTO.setPassword(object[PASSWORD].toString());
        });

        return responseDTO;
    };

    public static Function<UserRequestDTO, String> createQueryToFetchUserDetails = (requestDTO) -> {

        String query = "";

        query = " SELECT a.id," +
                " a.password," +
                " FROM" +
                " users a" +
                " WHERE" +
                " a.id!=0";

        if (!Objects.isNull(requestDTO.getUsername()))
            query += " AND a.username= '" + requestDTO.getUsername() + "'";

        return query;
    };
    public UserResponseDTO searchUser(UserRequestDTO requestDTO) {
        List<Object[]> results = entityManager.createNativeQuery(
                createQueryToFetchUserDetails.apply(requestDTO)).getResultList();
        return convertToUserResponse.apply(results);
    }

    public AppUser updateUser(UserRequestDTO requestDTO) {
        AppUser user = this.securityRepository.getUserById(requestDTO.getId()).orElseThrow(() -> {
            return new RuntimeException();
        });
        return securityRepository.save(user);
    }
}
