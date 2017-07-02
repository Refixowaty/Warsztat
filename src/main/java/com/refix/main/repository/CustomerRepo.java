package com.refix.main.repository;

import com.refix.main.entity.Customer;
import com.refix.main.entity.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CustomerRepo extends JpaRepository<Customer, Long>{

    public Customer findByNameAndLastName(String name, String lastName);

    @Query(value = "SELECT * FROM CUSTOMER customer WHERE customer.business_client = :businessClient", nativeQuery = true)
    public List<Customer> findAllClientsByKind(@Param("businessClient") boolean businessClient);

}
