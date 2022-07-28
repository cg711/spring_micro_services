package com.example.batches.controller;

import com.example.batches.service.BatchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class BatchesController {

    @Autowired
    BatchesService batchesService;

    @GetMapping("/student/{fileName}")
    public void studentBatch(@PathVariable String fileName) {
        batchesService.callStudentSchedule(fileName);
    }
}
