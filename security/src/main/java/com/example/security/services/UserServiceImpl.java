package com.example.security.services;

import com.example.security.dtos.UserRequestDTO;
import com.example.security.dtos.UserResponseDTO;
import com.example.security.model.AppUser;
import com.example.security.repository.SecurityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
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

    /**
     * Converts an object array into a UserResponseDTO object.
     */
    public static Function<Object[], UserResponseDTO> convertToResponse = (object) -> {
        final Integer ADMIN_ID = 0;

        return UserResponseDTO.builder()
                .id(Long.parseLong(object[ADMIN_ID].toString()))
                .build();
    };

    /**
     * Converts a list of object arrays to UserResponseDTO objects.
     */
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

    /**
     * Fetches a user object from the database matching a specified username.
     */
    public static Function<UserRequestDTO, String> createQueryToFetchUserDetails = (requestDTO) -> {

        String query = "";

        query = "SELECT *" +
                " FROM" +
                " users" +
                " WHERE";

        if (!Objects.isNull(requestDTO.getUsername()))
            query += " username= '" + requestDTO.getUsername() + "'";
        return query;
    };

    /**
     * Searches for a user in the database and returns the response.
     * @param requestDTO Attributes of user to be found.
     * @return Response DTO of found user.
     */
    public UserResponseDTO searchUser(UserRequestDTO requestDTO) {
        List<Object[]> results = entityManager.createNativeQuery(
                createQueryToFetchUserDetails.apply(requestDTO)).getResultList();
        return convertToUserResponse.apply(results);
    }


    /**
     * Updates a user into the database.
     * @param requestDTO User attributes to be updated.
     * @return Saved user in database.
     */
    @Override
    public AppUser updateUser(UserResponseDTO requestDTO) {
        AppUser user = this.securityRepository.getUserById(requestDTO.getId()).orElseThrow(() -> {
            return new RuntimeException();
        });
        return securityRepository.save(user);
    }
}
