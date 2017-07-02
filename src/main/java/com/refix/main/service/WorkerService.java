package com.refix.main.service;


import com.refix.main.entity.Worker;
import com.refix.main.repository.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {

    @Autowired
        private WorkerRepo workerRepo;

    public Worker createWorker(String name, String lastName, String workPosition, String phoneNumber, String pesel){
        Worker worker = new Worker(name, lastName, workPosition, phoneNumber, pesel);
        workerRepo.save(worker);
        return worker;
    }

    public Worker createWorker(String name, String lastName, String workPosition, String phoneNumber, String pesel, String bankAccountNumber, String street, String zipCode, String city){
        Worker worker = new Worker(name, lastName, workPosition, phoneNumber, pesel, bankAccountNumber, street, zipCode, city);
        workerRepo.save(worker);
        return worker;
    }

    public void saveWorker(Worker worker){
        workerRepo.save(worker);
    }

    public Worker readWorker(long id){
        return workerRepo.findOne(id);
    }

    public Worker readWorker(String pesel){
        return workerRepo.findByPesel(pesel);
    }

    public List<Worker> readAllWorkers(){
        return workerRepo.findAll();
    }

    public List<Worker> readEmployedWorkers() {
        return workerRepo.findAllEmployedWorkers();
    }

    public void updateWorker(long id, String name, String lastName, String workPosition, String phoneNumber, String pesel){
        Worker worker = workerRepo.findOne(id);
        worker.setName(name);
        worker.setLastName(lastName);
        worker.setWorkPosition(workPosition);
        worker.setPhoneNumber(phoneNumber);
        worker.setPesel(pesel);
    }

    public void updateWorker(long id, String name, String lastName, String workPosition, String phoneNumber,
                             String pesel, String bankAccountNumber){
        Worker worker = workerRepo.findOne(id);
        worker.setName(name);
        worker.setLastName(lastName);
        worker.setWorkPosition(workPosition);
        worker.setPhoneNumber(phoneNumber);
        worker.setPesel(pesel);
        worker.setBankAccountNumber(bankAccountNumber);
    }

    public void updateWorker(long id, String name, String lastName, String workPosition, String phoneNumber,
                             String pesel, String bankAccountNumber, String street, String zipCode, String city){
        Worker worker = workerRepo.findOne(id);
        worker.setName(name);
        worker.setLastName(lastName);
        worker.setWorkPosition(workPosition);
        worker.setPhoneNumber(phoneNumber);
        worker.setPesel(pesel);
        worker.setBankAccountNumber(bankAccountNumber);
        worker.setStreet(street);
        worker.setZipCode(zipCode);
        worker.setCity(city);
    }

    public void deleteWorker(long id){
        workerRepo.delete(id);
    }

    public void deleteWorker(String pesel){
        Worker worker = workerRepo.findByPesel(pesel);
        workerRepo.delete(worker);
    }

    public void deleteWorker(Worker worker){
        workerRepo.delete(worker);
    }
}
