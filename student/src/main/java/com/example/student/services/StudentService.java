package com.example.student.services;


import com.example.student.model.Student;

public interface StudentService {

    Student saveOrUpdate (Student student);

    Student findById(Long id);

    void deleteById(Long id);
}
