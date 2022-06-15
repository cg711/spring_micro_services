package com.example.student.controller;

import com.example.student.mapper.StudentDTO;
import com.example.student.mapper.StudentMapper;
import com.example.student.model.Student;
import com.example.student.services.StudentService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    ApplicationContext applicationContext;

    @PostMapping("createStudent")
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveOrUpdate(student);
    }

    @GetMapping("findStudentById/{studentId}")
    public StudentDTO findStudentById(@PathVariable("studentId") long studentId) {
        Student studentData = studentService.findById(studentId);
        return StudentMapper.toStudentDTO(studentData);
    }
//
//    @GetMapping("/testBatch")
//    public void testBatch() {
//        Map<String, JobParameter> map = new HashMap<>();
//        map.put("startTime", new JobParameter(System.currentTimeMillis()));
//        JobParameters jobParameters = new JobParameters(map);
//        try {
//            jobLauncher.run((Job)applicationContext.getBean("ETL"), jobParameters);
//        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
//                 JobParametersInvalidException e) {
////            AppLog.builder();
////            AppLog.build
////                    ...
//        }
//    }

    @GetMapping("/testBatch/{fileName}")
    public void testBatchWithFile(@PathVariable("fileName") String fileName) {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("startTime", new JobParameter(System.currentTimeMillis()));
        map.put("fileName", new JobParameter(fileName));
        JobParameters jobParameters = new JobParameters(map);
        try {
            jobLauncher.run((Job)applicationContext.getBean("ETL"), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            throw new RuntimeException(e);
//            AppLog.build()
            //TODO IMPLEMENT APPLOG FUNCTIONALITY
//                    ...
        }
    }

    @DeleteMapping("deleteStudentById/{studentId}")
    public void deleteStudentById(@PathVariable("studentId") long studentId){
        studentService.deleteById(studentId);
    }
}
