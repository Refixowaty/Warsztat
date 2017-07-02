package com.refix.main.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.refix.main.rest.json.GsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @GsonIgnore
    private Customer owner;
    private String brand;
    private String model;
    private String engine;
    private String regNum;
    private String prodYear;

    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @GsonIgnore
    @JsonIgnore
    private List<ServiceOrder> serviceOrders = new ArrayList<>();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public String getProdYear() {
        return prodYear;
    }

    public void setProdYear(String prodYear) {
        this.prodYear = prodYear;
    }

    public List<ServiceOrder> getServiceOrders() {
        return serviceOrders;
    }

    public void setServiceOrders(List<ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }

    public Car(Customer owner, String brand, String model, String engine, String regNum, String prodYear) {
        this.owner = owner;
        this.brand = brand;
        this.model = model;
        this.engine = engine;
        this.regNum = regNum;
        this.prodYear = prodYear;
    }

    public Car(){}

    @Override
    public String toString() {
        return brand + " " + model +
                " " + engine + " " + prodYear + " " + regNum;
    }

}
