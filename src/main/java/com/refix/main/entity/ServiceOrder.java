package com.refix.main.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class ServiceOrder{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;
    private double cost;
    private Timestamp date;
    private boolean finished;

    @ManyToOne
    private Car car;
    @ManyToOne
    private Worker worker;

    @OneToMany(mappedBy = "serviceOrder")
    private List<PartUse> partUses = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public List<PartUse> getPartUses() {
        return partUses;
    }

    public void setPartUses(List<PartUse> partUses) {
        this.partUses = partUses;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public ServiceOrder(Car car, String description) {
        this.car = car;
        this.description = description;
        date = new Timestamp(new Date().getTime());
        finished = false;
    }

    public ServiceOrder(Car car, String description, Worker worker){
        this(car, description);
        this.worker = worker;
    }

    public ServiceOrder(Car car, String description, double cost) {
        this(car, description);
        this.cost = cost;
    }

    public ServiceOrder(Car car, String description, double cost, Worker worker){
        this(car, description, worker);
        this.cost = cost;
    }

    public ServiceOrder(Car car, String description, double cost, Worker worker, List<PartUse> partUses){
        this(car, description, cost, worker);
        this.partUses = partUses;
    }

    public ServiceOrder(){}

    @Override
    public String toString() {
        return "ORDER:   " +
                "ORDER ID: " + id +
                ", CAR ID: " + car.getId() +
                ", TYPE OF SERVICE: '" + description + '\'' +
                ", PRICE: " + cost +
                "\n WORKER TO REALIZE THIS ORDER: " + worker.getLastName()+ "Date: " + date +
                '}' + " " + partUses + "\n\n";
    }


}
