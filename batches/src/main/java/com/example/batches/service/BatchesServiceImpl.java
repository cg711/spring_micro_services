package com.example.batches.service;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class BatchesServiceImpl implements BatchesService {
    /**
     * Schedules a batch job within the scheduler microservice.
     * @param fileName name of file to be processed.
     */
    @Override
    public void callStudentSchedule(String fileName) {

        final String URL = "http://localhost:8087/scheduler/schedule";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("fileName", fileName);
            jsonObject.put("port", "8087");
            jsonObject.put("microName", "batch");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<String>(jsonObject.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(URL, request, String.class);
    }
}
