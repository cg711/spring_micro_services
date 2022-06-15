package com.example.student.services;


import com.example.student.model.Student;
import org.codehaus.jettison.json.JSONException;

public interface StudentService {

    Student saveOrUpdate (Student student);

    Student findById(Long id);

    void deleteById(Long id);

    void sendAppLog(String message, Long time) throws JSONException;
}
