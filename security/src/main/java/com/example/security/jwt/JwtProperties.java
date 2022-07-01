package com.example.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@ConfigurationProperties(prefix = "jwt")
@Data
@Configuration
public class JwtProperties implements Serializable {

    private String secretKey;

}
