package com.example.student.services;

import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    StudentRepository studentRepository;
    @Override
    public Student saveOrUpdate(Student student) {
        if(student.getId() != null){
            Student studentData = studentRepository.getOne(student.getId());
            if(studentData == null) {
                return null;
            }
            studentData.setFirstName(student.getFirstName());
            studentData.setLastName(student.getLastName());
            studentData.setSsn(student.getSsn());
            studentData.setGrade(student.getGrade());
            return studentRepository.save(studentData);
        }
        return studentRepository.save(student);
    }

    @Override
    public Student findById(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        return studentOptional.orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}
