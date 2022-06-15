package com.example.applog.exceptions;

import com.example.applog.model.AppLog;

public class NotFoundException extends Exception {

    private Class className;
    private Long id;
    public NotFoundException() {

    }
    public NotFoundException(Class<AppLog> className, Long id) {
        this.className = className;
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s with id %s not found.", className, id);
    }
}
