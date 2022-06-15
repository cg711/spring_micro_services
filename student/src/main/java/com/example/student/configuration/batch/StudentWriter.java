package com.example.student.configuration.batch;

import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

    @Component
    public class StudentWriter implements ItemWriter<Student> {
        @Autowired
        StudentRepository studentRepository;
        @Override
        public void write(List<? extends Student> list) throws Exception {
            for (Student student : list) {
                System.out.println(String.format("StudentWriter:\n\tWriting data: %s %s %s %s", student.getFirstName(), student.getLastName(), student.getSsn(), student.getGrade()));
                studentRepository.save(student);
            }
        }
    }

