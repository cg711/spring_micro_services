package com.example.student.services;

import com.example.student.exceptions.NotFoundException;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * Updates or saves a Student object to the database.
     * @param student Student object to be updated or saved in the database.
     * @return Student object updated or saved in the database.
     */
    @Override
    public Student saveOrUpdate(Student student) throws NotFoundException {
        if(student.getId() != null){
            Student studentData = findById(student.getId());
            if(studentData != null) {
                studentData.setFirstName(student.getFirstName());
                studentData.setLastName(student.getLastName());
                studentData.setSsn(student.getSsn());
                studentData.setGrade(student.getGrade());
                return studentRepository.save(studentData);
            }
        }
        return studentRepository.save(student);
    }

    /**
     * Finds and returns a Student object matching the passed in id.
     * @param id Id of wanted Student.
     * @return Student object matching id.
     */
    @Override
    public Student findById(Long id) throws NotFoundException {
        Optional<Student> studentOptional = studentRepository.findById(id);
        return studentRepository.findById(id).orElseThrow(() -> new NotFoundException(Student.class, id));
    }

    /**
     * Finds and deletes a Student object from the database matching the passed in id.
     * @param id Id of wanted Student.
     */
    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    /**
     * Sends a JSON object to the applog microservice including the time of an error aswell
     * as the message to go along with the error.
     * @param message Message bundled with error.
     * @param time Time of error.
     * @throws JSONException
     */
    @Override
    public void sendAppLog(String message, String time) throws JSONException {
        final String url = "http://localhost:8082/applog-services/createAppLog";
        JSONObject appLogJSON = new JSONObject();
        try {
            appLogJSON.put("message", message);
            appLogJSON.put("time", time);
        } catch (JSONException e){
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(appLogJSON.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, request, String.class);
    }

    /**
     * Starts a batch process on a given file reference by fileName.
     * @param fileName Name of the file to be processed.
     */
    @Override
    public void batchProcess(String fileName) {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("startTime", new JobParameter(System.currentTimeMillis()));
        map.put("fileName", new JobParameter(fileName));
        JobParameters jobParameters = new JobParameters(map);
        String time = new Date().toString();
        try {
            jobLauncher.run((Job)applicationContext.getBean("ETL"), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            try {
                sendAppLog(e.getMessage(), time);
            } catch (JSONException err) {
                throw new RuntimeException(err);
            }
        }
    }
}
