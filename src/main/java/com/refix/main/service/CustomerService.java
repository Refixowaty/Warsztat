package com.refix.main.service;

import com.refix.main.entity.Car;
import com.refix.main.entity.Customer;
import com.refix.main.repository.CarRepo;
import com.refix.main.repository.CustomerRepo;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private CarRepo carRepo;

    public Customer createCustomer(String name, String lastName, String phoneNumber, String email, boolean businessClient){
        Customer customer = new Customer(name, lastName, phoneNumber, email, businessClient);
        customerRepo.save(customer);
        return customer;
    }

    public Customer createBusinessCustomer(String name, String lastName, String phoneNumber, String email,
                                           String company, String address, String nip, String regon, boolean businessClient){
        Customer customer = new Customer(name, lastName, phoneNumber, email, company, address, nip ,regon, businessClient);
        customerRepo.save(customer);
        return customer;
    }

    public void saveCustomer(Customer customer){
        customerRepo.save(customer);
    }

    public Customer readCustomer(long id){
        return customerRepo.getOne(id);
    }

    public Customer readCustomer(String name, String lastName){
        return customerRepo.findByNameAndLastName(name, lastName);
    }

    public List<Customer> findAllClientsByKind(boolean businessClient){
        return customerRepo.findAllClientsByKind(businessClient);
    }

    public List<Customer> readAllCustomers(){
        return customerRepo.findAll();
    }

    public void updateCustomer(long id, String name, String lastName, String phoneNumber, String email) {
        Customer customer = customerRepo.findOne(id);
        customer.setName(name);
        customer.setLastName(lastName);
        customer.setPhoneNumber(phoneNumber);
        customer.setEmail(email);
        customerRepo.save(customer);
    }

    public void updateCustomer(Customer customer, String name, String lastName, String phoneNumber, String email) {
        customer.setName(name);
        customer.setLastName(lastName);
        customer.setPhoneNumber(phoneNumber);
        customer.setEmail(email);
        customerRepo.save(customer);
    }

    public void deleteCustomer(long id){
        customerRepo.delete(id);
    }

    public void deleteCustomer(Customer customer) {
        customerRepo.delete(customer);
    }

    //liczba aut przypisanych do customera
    public long getCustomerCarAmount(long customerId){
        return carRepo.getCustomerCarAmount(customerId);
    }

    @Transactional
    public List<Car> getCustomerCars(Customer customer){
        Hibernate.initialize(customer.getCars());
        return customer.getCars();
    }

    public void addCarForCustomer(long customerID, Car car){
        Customer customer = customerRepo.findOne(customerID);
        car.setOwner(customer);
        carRepo.save(car);
    }




}
