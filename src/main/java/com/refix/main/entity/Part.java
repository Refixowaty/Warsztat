package com.refix.main.entity;

import javax.persistence.*;

@Entity
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private double cost;
    private String producer;
    private String category;
    private boolean newPart;
    private int quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public boolean isNewPart() {
        return newPart;
    }

    public void setNewPart(boolean newPart) {
        this.newPart = newPart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Part(String name, double cost, String producer, String category, boolean newPart, int quantity) {
        this.name = name;
        this.cost = cost;
        this.producer = producer;
        this.category = category;
        this.newPart = newPart;
        this.quantity = quantity;
    }

    public Part(){}

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                ", producer='" + producer + '\'' +
                ", category='" + category + '\'' +
                ", newPart=" + newPart +
                ", quantity=" + quantity +
                '}';
    }
}
