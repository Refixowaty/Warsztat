package com.refix.main.rest.controllers;

import com.refix.main.entity.ServiceOrder;
import com.refix.main.service.ServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/orders")
@RestController()
public class ServiceOrderRestController {

    @Autowired
    ServiceOrderService serviceOrderService;

    @RequestMapping(value = "/active")
    public List<ServiceOrder> readActiveOrders(){
        return serviceOrderService.readActiveServiceOrders();
    }

}
