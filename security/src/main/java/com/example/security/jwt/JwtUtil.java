package com.example.security.jwt;

import com.example.security.model.AppUser;
import com.example.security.repository.SecurityRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
@Service
public class JwtUtil implements Serializable {
    private String secretKey;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    SecurityRepository securityRepository;


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }

    /**
     * Creates a JWT.
     * @param username Name of user.
     * @param request Request body.
     * @return JWT in String form.
     */
    public String createJWT(String username, HttpServletRequest request) {
        AppUser user = securityRepository.fetchUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not Found."));
        String jwt = Jwts.builder()
                .setSubject(username)
                .setIssuer("admin")
                .setExpiration(calculateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.US_ASCII)))
                .compact();
        user.setJwt(jwt);
        securityRepository.save(user);
        return jwt;
    }

    /**
     * Determines the experation date of the JWT for 5 hours after its creation.
     * @return Experation date of JWT.
     */
    private Date calculateExpirationDate() {
        Date current = new Date();
        return new Date(current.getTime() + (1000 * 3600 * 5));
    }
}
