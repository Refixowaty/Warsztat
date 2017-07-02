package com.refix.main.rest.controllers;

import com.refix.main.entity.Customer;
import com.refix.main.rest.json.JsonUtils;
import com.refix.main.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerRestController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/all")
    public String readAllCustomers(){
        List<Customer> customers = customerService.readAllCustomers();
        return JsonUtils.getJsonFromObject(customers);
    }

}
