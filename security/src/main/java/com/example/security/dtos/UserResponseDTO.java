package com.example.security.dtos;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserResponseDTO implements Serializable {
    private Long id;
    private String password;
}
