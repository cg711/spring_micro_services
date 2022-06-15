package com.example.student.services;

import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Override
    public void sendAppLog(String message, Long time) throws JSONException {
        String url = "http://localhost:8082/applog-services/createAppLog";
        JSONObject appLogJSON = new JSONObject();
        try {
            appLogJSON.put("message", message);
            appLogJSON.put("time", time);
        } catch (JSONException e){
            e.printStackTrace();
        }
//        System.out.println(appLogJSON.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(appLogJSON.toString(), headers);
//        System.out.println(request.toString());
        RestTemplate restTemplate = new RestTemplate();
        String jsonReturn = restTemplate.postForObject("http://localhost:8082/applog-services/createAppLog", request, String.class);
    }
}
