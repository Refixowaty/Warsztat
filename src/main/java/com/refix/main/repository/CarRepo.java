package com.refix.main.repository;

import com.refix.main.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CarRepo extends JpaRepository<Car, Long> {

    @Query(value = "SELECT * FROM Car car WHERE car.owner_id = :customerId", nativeQuery = true)
    public List<Car> findAllOwnerCar(@Param("customerId") long customerId);

    @Query(value = "SELECT COUNT(Car.owner_id) FROM Car car WHERE car.owner_id = :customerId", nativeQuery = true)
    public long getCustomerCarAmount(@Param("customerId") long customerId);

    public Car findByRegNum(String regNum);

}
