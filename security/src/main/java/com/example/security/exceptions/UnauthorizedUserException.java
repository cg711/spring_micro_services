package com.example.security.exceptions;

import com.example.security.model.AppUser;

public class UnauthorizedUserException extends Exception {
    private Class className;

    public UnauthorizedUserException() {

    }
    public UnauthorizedUserException(Class<AppUser> className) {
        this.className = className;
    }
    @Override
    public String toString() {
        return String.format("User is unauthorized.");
    }
}
