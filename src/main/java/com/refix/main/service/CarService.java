package com.refix.main.service;

import com.refix.main.entity.Car;
import com.refix.main.entity.Customer;
import com.refix.main.entity.ServiceOrder;
import com.refix.main.repository.CarRepo;
import com.refix.main.repository.ServiceOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepo carRepo;
    @Autowired
    private ServiceOrderRepo serviceOrderRepo;

    public Car createCar(Customer owner, String brand, String model, String engineCapacity,String regNum, String prodYear){
        Car car = new Car(owner, brand, model, engineCapacity, regNum, prodYear);
        carRepo.save(car);
        return car;
    }

    public void saveCar(Car car){
        carRepo.save(car);
    }

    public Car readCar(long id){
        return carRepo.findOne(id);
    }

    public Car readCar(String regNum){
        return carRepo.findByRegNum(regNum);
    }

    public List<Car> readAllCars(){
        return carRepo.findAll();
    }

    public void updateCar(long carId, String regNum, Customer owner, String brand, String model, String engineCapacity, String prodYear){
        Car car = carRepo.findOne(carId);
        car.setRegNum(regNum);
        car.setOwner(owner);
        car.setBrand(brand);
        car.setModel(model);
        car.setEngine(engineCapacity);
        car.setProdYear(prodYear);
    }

    public void updateCar(String regNum, Customer owner, String brand, String model, String engineCapacity, String prodYear){
        Car car = carRepo.findByRegNum(regNum);
        car.setRegNum(regNum);
        car.setOwner(owner);
        car.setBrand(brand);
        car.setModel(model);
        car.setEngine(engineCapacity);
        car.setProdYear(prodYear);
    }

    public void deleteCar(long id){
        carRepo.delete(id);
    }

    public void deleteCar(String regNum){
        Car car = carRepo.findByRegNum(regNum);
        carRepo.delete(car);
    }

    public List<Car> findAllCustomerCars(long id){
        return carRepo.findAllOwnerCar(id);
    }

    public List<ServiceOrder> findAllOrdersForCar(long id){
        return serviceOrderRepo.findAllOrdersForCar(id);
    }
}
