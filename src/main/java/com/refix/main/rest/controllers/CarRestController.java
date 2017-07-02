package com.refix.main.rest.controllers;

import com.refix.main.entity.Car;
import com.refix.main.entity.ServiceOrder;
import com.refix.main.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/cars")
public class CarRestController {
    @Autowired
    CarService carService;

    @RequestMapping(value = "/all")
    public List<Car> readAllCars(){
        return carService.readAllCars();
    }

    @RequestMapping("/{id}/orders")
    public List<ServiceOrder> readCarOrders(@PathVariable("id") Long id){
        return carService.findAllOrdersForCar(id);
    }


}
