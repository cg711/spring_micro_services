package com.example.student.services;

import com.example.student.exceptions.NotFoundException;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.core.io.Resource;
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

    public static Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
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
                Student tempStudent =  studentRepository.save(studentData);
                sendAppLog("Successfully updated student " + student.getFirstName() + " in database.", getCurrentTime());
                return tempStudent;
            }
        }
        sendAppLog("Succesfully added student " + student.getFirstName() + "into database.", getCurrentTime());
        return studentRepository.save(student);
    }

    /**
     * Finds and returns a Student object matching the passed in id.
     * @param id Id of wanted Student.
     * @return Student object matching id.
     */
    @Override
    public Student findById(Long id) throws NotFoundException {
        return studentRepository.findById(id).orElseThrow(() -> new NotFoundException(Student.class, id));
    }

    /**
     * Finds and deletes a Student object from the database matching the passed in id.
     * @param id Id of wanted Student.
     */
    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
        sendAppLog("Attempted to delete student with id " + id.toString(), getCurrentTime());
    }

    /**
     * Sends a JSON object to the applog microservice including the time of an error aswell
     * as the message to go along with the error.
     * @param message Message bundled with error.
     * @param time Time of error.
     */
    @Override
    public void sendAppLog(String message, String time) {
        final String url = "http://localhost:8082/applog/createAppLog";

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
    @Override
    public int getStatus(Exception e) {
        final String url = "http://localhost:8085/common-services/handleExceptions";
        JSONObject statusJson  = new JSONObject();
        try {
            statusJson.put("exception", e.getClass().toString());
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(statusJson.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, request, Integer.class);
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
        try {
            jobLauncher.run((Job)applicationContext.getBean("ETL"), jobParameters);
            sendAppLog("Succesfully executed batch job", getCurrentTime());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            sendAppLog(e.getMessage(), getCurrentTime());
        }
    }

    public void callBatchJob(String fileName) {
        final String URL = "http://localhost:8086/batch/student/" + fileName;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(URL, String.class);
    }

    public String getCurrentTime() {
        return new Date().toString();
    }

}


//TODO Microservices for scheduling batches and multithreading
//START: gateway timeout (504 error) -> load balancer (circuit breaker), uses hystrix
//Thread will execute every 5 minutes, will check database for new entires (jobs)
//  name service scheduler & batches
//  if job, will put in scheduler and then will schedule job for respective prone expression
//  JobListener: beforeJob() -executes before job is executed, afterJob() -executes after job
//  before schedule A, after schedule B, after B schedule C etc.

//  where to enable hsytrix dashboard, main file

//scheduler configurer
//public syncronized void