package com.example.student.services;


import com.example.student.exceptions.NotFoundException;
import com.example.student.model.Student;
import org.codehaus.jettison.json.JSONException;

import java.util.Date;

public interface StudentService {

    Student saveOrUpdate (Student student) throws NotFoundException;

    Student findById(Long id) throws NotFoundException;

    void deleteById(Long id);

    void sendAppLog(String message, String time);

    void batchProcess(String filename);

    String getCurrentTime();
}
