package com.refix.main.rest.controllers;

import com.refix.main.entity.Worker;
import com.refix.main.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Bzyku on 28.05.2017.
 */
@RestController
@RequestMapping("/employees")
public class WorkerRestController {
    @Autowired
    WorkerService workerService;

    @RequestMapping("/all")
    public List<Worker> readAllWorkers(){
        return workerService.readEmployedWorkers();
    }
}
