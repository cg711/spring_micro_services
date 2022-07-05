package com.example.security.jwt;

import com.example.security.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtTokenProvider {
    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    private UserService userDetailsService;
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }

    /**
     * Gives the current authentication of the service.
     * @param token JWT token.
     * @return A new UsernamePasswordAuthenticationToken.
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Returns the username of the current user.
     * @param token JWT token.
     * @return Username of user.
     */
    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Parses the JWT token from the authorization header.
     * @param req Http request.
     * @return String JWT token.
     */
    public String parseToken(HttpServletRequest req) {
        String bearer = req.getHeader("Bearer");
        if(!Objects.isNull(bearer) && bearer.startsWith("Bearer")) {
            return bearer.substring(7);
        }
        return null;
    }

    /**
     * Attempts to validate a JWT token based on its expiration.
     * @param token
     * @return
     * @throws Exception
     */
    public boolean validateToken(String token) throws Exception {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new Exception("Unauthorized User");
        }
    }
}
