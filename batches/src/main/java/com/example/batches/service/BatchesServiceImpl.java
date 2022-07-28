package com.example.batches.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BatchesServiceImpl implements BatchesService {
    /**
     * Schedules a batch job within the scheduler microservice.
     * @param fileName name of file to be processed.
     */
    @Override
    public void callStudentSchedule(String fileName) {

        final String URL = "http://localhost:8087/scheduler/scheduleStudent";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(URL, String.class);
    }
}
