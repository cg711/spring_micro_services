package com.example.applog.controller;

import com.example.applog.model.AppLog;
import com.example.applog.services.AppLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applog-services")
public class AppLogController {

    @Autowired
    AppLogService appLogService;

    @PostMapping("createAppLog")
    public AppLog createAppLog(@RequestBody AppLog appLog) {
        return appLogService.saveOrUpdate(appLog);
    }
}
