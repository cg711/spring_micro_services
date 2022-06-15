package com.example.student.mapper;

import com.example.student.model.Student;

public class StudentMapper {

    /**
     * Converts a StudentDTO object to a Student object.
     * @param studentDTO StudentDTO object to be converted.
     * @return Student object converted from StudentDTO object.
     */
    public static Student toStudent(StudentDTO studentDTO) {
        if (studentDTO == null) {
            return null;
        }
        return Student.builder()
                .id(studentDTO.getId())
                .firstName(studentDTO.getFirstName())
                .lastName(studentDTO.getLastName())
                .ssn(studentDTO.getSsn())
                .grade(studentDTO.getGrade())
                .build();
    }

    /**
     * Converts a Student object to a StudentDTO object.
     * @param student Student object to be converted.
     * @return StudentDTO object converted from Student object.
     */
    public static StudentDTO toStudentDTO(Student student) {
        if (student == null) {
            return null;
        }

        return StudentDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .ssn(student.getSsn())
                .grade(student.getGrade())
                .build();

    }

    /**
     * Updates or saves a Student object to or in the database.
     * @param student Original Student object.
     * @param updateStudent Updated Student object.
     * @return Original updated Student object.
     */
    public static Student toUpdateStudent(Student student, Student updateStudent) {
        student.setFirstName(updateStudent.getFirstName() == null ? student.getFirstName() : updateStudent.getFirstName());
        student.setLastName(updateStudent.getLastName() == null ? student.getLastName() : updateStudent.getLastName());
        student.setSsn(updateStudent.getSsn() == null ? student.getSsn() : updateStudent.getSsn());
        student.setGrade(updateStudent.getGrade() == null ? student.getGrade() : updateStudent.getGrade());
        return student;
    }
}
