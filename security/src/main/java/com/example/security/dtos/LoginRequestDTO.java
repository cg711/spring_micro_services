package com.example.security.dtos;

import lombok.*;

@Getter
@Setter
public class LoginRequestDTO {

    private String credential;
    private String password;

}
