package com.refix.main.rest.controllers;

import com.refix.main.entity.Part;
import com.refix.main.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parts")
public class PartRestController {
    @Autowired
    PartService partService;

    @RequestMapping("/all")
    public List<Part> readAllParts(){
        return partService.readAllPartWithPlusQuantity();
    }
}
