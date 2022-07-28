package com.example.scheduler.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@Service
public class SchedulerServiceImpl implements SchedulerService {
    /**
     * Scheduled every 5 minutes to check the database to see if there are any new job executions, and then executes them.
     * @throws Exception
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void perform() throws Exception {
        final String URL = "http://localhost:8081/student/batch/grades.csv";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(URL, String.class);
    }

}

// called every minute
// checks DB if new job exection
// if so, executes in order of first to last
// can call batch process in student


