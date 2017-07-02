package com.refix.main.repository;


import com.refix.main.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface WorkerRepo extends JpaRepository<Worker, Long>{

    public Worker findByPesel(String pesel);

    @Query(value = "SELECT * FROM WORKER worker WHERE worker.employed = TRUE", nativeQuery = true)
    public List<Worker> findAllEmployedWorkers();   //TODO: USUN KOMENTARZ
}
