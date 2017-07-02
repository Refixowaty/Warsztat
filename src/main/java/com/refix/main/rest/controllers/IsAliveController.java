package com.refix.main.rest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IsAliveController {

    @RequestMapping("/isAlive")
    public boolean isAlive(){
        return true;
    }
}
