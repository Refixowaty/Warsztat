package com.refix.main.repository;


import com.refix.main.entity.ServiceOrder;
import com.refix.main.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceOrderRepo extends JpaRepository<ServiceOrder, Long>{

    @Query(value = "SELECT * FROM SERVICE_ORDER serviceOrder WHERE serviceOrder.car_id = :carId", nativeQuery = true)
    public List<ServiceOrder> findAllOrdersForCar(@Param("carId") long carId);

    @Query(value = "SELECT * FROM SERVICE_ORDER serviceOrder WHERE serviceOrder.finished = FALSE", nativeQuery = true)
    public List<ServiceOrder> findAllUnfinishedOrders();    //TODO: USUN KOMENTARZ


}
