package com.example.scheduler.service;

import com.example.scheduler.DTO.ScheduleRequestDTO;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
public class SchedulerServiceImpl implements SchedulerService {
    /**
     *
     * @param scheduleRequestDTO Request object with port, filename, and mircoservice name.
     *                           Scalable to any microservice that calls a batch job.
     * @throws Exception
     */
    @Scheduled(cron = "0 0/5 * * * *")
    public void perform(ScheduleRequestDTO scheduleRequestDTO) throws Exception {
        final String URL = "http://localhost:" + scheduleRequestDTO.getPort() + "/" + scheduleRequestDTO.getMicroName() + "/" + scheduleRequestDTO.getFileName();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(URL, String.class);

    }

}
