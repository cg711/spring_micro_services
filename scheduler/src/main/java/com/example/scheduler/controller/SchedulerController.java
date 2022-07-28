package com.example.scheduler.controller;

import com.example.scheduler.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {
    @Autowired
    SchedulerService schedulerService;

    @GetMapping("/scheduleStudent")
    public void recieveScheduleRequest() {
        try {
            schedulerService.perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
