package com.example.scheduler.controller;

import com.example.scheduler.DTO.ScheduleRequestDTO;
import com.example.scheduler.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/scheduler")
public class SchedulerController {
    @Autowired
    SchedulerService schedulerService;

    @PostMapping("/schedule")
    public void recieveScheduleRequest(@RequestBody ScheduleRequestDTO scheduleRequestDTO) {
        try {
            schedulerService.perform(scheduleRequestDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
