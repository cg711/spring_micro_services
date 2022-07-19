package com.example.common_service.controller;

import com.example.common_service.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/common")
public class CommonServiceController {
    @Autowired
    CommonService commonService;

    @PostMapping("/handleException")
    public int handleException(@RequestBody String req) {
        return commonService.handleException(req);
    }

}