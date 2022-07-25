package com.example.scheduler.service;

import com.example.scheduler.DTO.ScheduleRequestDTO;

public interface SchedulerService {
    public void perform(ScheduleRequestDTO scheduleRequestDTO) throws Exception;
}
