package com.example.student.configuration.batch;

import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentWriter implements ItemWriter<Student> {
    @Autowired
    StudentRepository studentRepository;

    private final static Logger logger = LoggerFactory.getLogger(StudentWriter.class);

    /**
     * Initializes the writer to be used within Student batch processing.
     * @param list The mapped file to be read
     * @throws Exception
     */
    @Override
    public void write(List<? extends Student> list) throws Exception {
        for (Student student : list) {
            logger.debug(String.format("StudentWriter:\n\tWriting data: %s %s %s %s", student.getFirstName(), student.getLastName(), student.getSsn(), student.getGrade()));
            studentRepository.save(student);
        }
    }
}

