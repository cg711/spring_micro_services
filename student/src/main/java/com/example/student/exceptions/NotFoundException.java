package com.example.student.exceptions;

import com.example.student.model.Student;
import org.hibernate.annotations.NotFound;

public class NotFoundException extends Exception {

    private Class className;
    private Long id;
    public NotFoundException() {

    }
    public NotFoundException(Class<Student> className, Long id) {
        this.className = className;
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s with id %s not found.", className, id);
    }
}
