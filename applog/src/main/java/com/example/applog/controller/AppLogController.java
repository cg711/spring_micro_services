package com.example.applog.controller;

import com.example.applog.exceptions.NotFoundException;
import com.example.applog.model.AppLog;
import com.example.applog.services.AppLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applog")
public class AppLogController {

    @Autowired
    AppLogService appLogService;

    /**
     * Creates a post request to save or update an AppLog
     * @param appLog AppLog to be saved
     * @return Saved or updated AppLog
     * @throws NotFoundException
     */
    @PostMapping("createAppLog")
    public AppLog createAppLog(@RequestBody AppLog appLog) throws NotFoundException {
        return appLogService.saveOrUpdate(appLog);
    }
}
