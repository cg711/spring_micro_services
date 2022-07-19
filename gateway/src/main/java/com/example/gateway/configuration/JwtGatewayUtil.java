package com.example.gateway.configuration;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Objects;


@Component
public class JwtGatewayUtil {
    @Value("${jwt.secretKey}")
    private String jwtSecret;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtGatewayUtil.class);

    public Claims getClaims(final String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
        return null;
    }
    public void validateToken(final String token) throws Exception {
        try {
            if(!token.isEmpty()) {
                return;
            }
//            String expired = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(parseToken(token)).getBody()..getExpiration().toString();
        } catch (SignatureException ex) {
            LOGGER.info("invalid signature");
            //exceptionMethod(ex.message
            throw new Exception("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            LOGGER.info("invalid token");
            throw new Exception("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            LOGGER.info("Expired");
            throw new Exception("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            LOGGER.info("unsupported");
            throw new Exception("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            LOGGER.info("empty");
            throw new Exception("JWT claims string is empty.");
        }
    }

    /**
     * Parses the JWT token from the authorization header, removes bearer.
     * @param token JWT in string form.
     * @return String JWT token.
     */
    public String parseToken(String token) {
        return token.split(" ")[1];
    }


}


