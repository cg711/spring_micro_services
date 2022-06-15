package com.example.student.controller;

import com.example.student.exceptions.NotFoundException;
import com.example.student.mapper.StudentDTO;
import com.example.student.mapper.StudentMapper;
import com.example.student.model.Student;
import com.example.student.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    /**
     * Inserts a Student into the database.
     * @param student Student object to be stored in database.
     * @return Created Student object.
     * @throws NotFoundException
     */
    @PostMapping("createStudent")
    public Student createStudent(@RequestBody Student student) throws NotFoundException {
        return studentService.saveOrUpdate(student);
    }

    /**
     * Finds a Student based off of an id.
     * @param studentId Id of Student to be found.
     * @return Found student in the database.
     * @throws NotFoundException
     */
    @GetMapping("findStudentById/{studentId}")
    public StudentDTO findStudentById(@PathVariable("studentId") long studentId) throws NotFoundException {
        Student studentData = studentService.findById(studentId);
        return StudentMapper.toStudentDTO(studentData);
    }

    /**
     * Starts a batch process on the given file.
     * @param fileName Name of file to be processed.
     */
    @GetMapping("/testBatch/{fileName}")
    public void testBatchWithFile(@PathVariable("fileName") String fileName) {
        studentService.batchProcess(fileName);
    }

    /**
     * Deletes a student in the database by the given id.
     * @param studentId Id of Student to be deleted.
     */
    @DeleteMapping("deleteStudentById/{studentId}")
    public void deleteStudentById(@PathVariable("studentId") long studentId){
        studentService.deleteById(studentId);
    }
}
