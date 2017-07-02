package com.refix.main.service;

import com.refix.main.entity.Car;
import com.refix.main.entity.PartUse;
import com.refix.main.entity.ServiceOrder;
import com.refix.main.entity.Worker;
import com.refix.main.repository.PartUseRepo;
import com.refix.main.repository.ServiceOrderRepo;
import com.refix.main.repository.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceOrderService {

    @Autowired
    private ServiceOrderRepo serviceOrderRepo;
    @Autowired
    private WorkerRepo workerRepo;
    @Autowired
    private PartUseRepo partUseRepo;

    public ServiceOrder createServiceOrder(Car car, String description, double cost, Worker worker){
        ServiceOrder serviceOrder = new ServiceOrder(car, description, cost, worker);
        serviceOrderRepo.save(serviceOrder);
        return serviceOrder;
    }

    public ServiceOrder createServiceOrder(Car car, String description, double cost){
        ServiceOrder serviceOrder = new ServiceOrder(car, description, cost);
        serviceOrderRepo.save(serviceOrder);
        return serviceOrder;
    }

    public ServiceOrder createServiceOrder(Car car, String description, Worker worker){
        ServiceOrder serviceOrder = new ServiceOrder(car, description, worker);
        serviceOrderRepo.save(serviceOrder);
        return serviceOrder;
    }

    public ServiceOrder createServiceOrder(Car car, String description){
        ServiceOrder serviceOrder = new ServiceOrder(car, description);
        serviceOrderRepo.save(serviceOrder);
        return serviceOrder;
    }

    public ServiceOrder readServiceOrder(long id){
        return serviceOrderRepo.findOne(id);
    }

    public List<ServiceOrder> readAllServiceOrders(){
        return serviceOrderRepo.findAll();
    }

    public List<ServiceOrder> readActiveServiceOrders(){    //TODO: USUN KOMENTARZ
        return serviceOrderRepo.findAllUnfinishedOrders();
    }

    public void updateServiceOrder(long id, Worker worker, Car car, String description){
        ServiceOrder serviceOrder = serviceOrderRepo.findOne(id);
        serviceOrder.setWorker(worker);
        serviceOrder.setCar(car);
        serviceOrder.setDescription(description);
        serviceOrderRepo.save(serviceOrder);
    }

    public void updateServiceOrder(long id, Car car, String description){
        ServiceOrder serviceOrder = serviceOrderRepo.findOne(id);
        serviceOrder.setCar(car);
        serviceOrder.setDescription(description);
        serviceOrderRepo.save(serviceOrder);
    }

    public void updateServiceOrder(ServiceOrder serviceOrder, Worker worker, Car car, String description){
        serviceOrder.setWorker(worker);
        serviceOrder.setCar(car);
        serviceOrder.setDescription(description);
        serviceOrderRepo.save(serviceOrder);
    }

    public void updateServiceOrder(ServiceOrder serviceOrder, Car car, String description){
        serviceOrder.setCar(car);
        serviceOrder.setDescription(description);
        serviceOrderRepo.save(serviceOrder);
    }

    public void updateServiceOrder(ServiceOrder serviceOrder, String description){
        serviceOrder.setDescription(description);
        serviceOrderRepo.save(serviceOrder);
    }

    public void updateServiceOrder(ServiceOrder serviceOrder, Worker worker){
        serviceOrder.setWorker(worker);
        serviceOrderRepo.save(serviceOrder);
    }

    public void updateServiceOrder(ServiceOrder serviceOrder, double cost){
        serviceOrder.setCost(cost);
        serviceOrder.setFinished(true);
        serviceOrderRepo.save(serviceOrder);

    }

    public void deleteServiceOrder(long id){
        serviceOrderRepo.delete(id);
    }

    public void deleteServiceOrder(ServiceOrder serviceOrder){
        serviceOrderRepo.delete(serviceOrder);
    }

    public void assignWorker(Worker worker, ServiceOrder serviceOrder){
        serviceOrder.setWorker(worker);
        serviceOrderRepo.save(serviceOrder);
    }

    public void assignWorker(long workerID, long serviceOrderID){
        Worker worker = workerRepo.findOne(workerID);
        ServiceOrder serviceOrder = serviceOrderRepo.findOne(serviceOrderID);
        serviceOrder.setWorker(worker);
        serviceOrderRepo.save(serviceOrder);
    }

    public List<PartUse> getServiceOrderPartUses(ServiceOrder serviceOrder){
        long orderID = serviceOrder.getId();
        List<PartUse> partUses = partUseRepo.findPartUsesOfOrder(orderID);
        return partUses;
    }

    public String showOrderSummary(long id){
        ServiceOrder serviceOrder = serviceOrderRepo.findOne(id);
        String result = serviceOrder.getCar().getOwner().getName() + " "
                + serviceOrder.getCar().getOwner().getLastName() + " "
                + serviceOrder.getCar().toString() + " "
                + serviceOrder.getDescription();
        return result;
    }

}
