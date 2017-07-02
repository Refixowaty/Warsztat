package com.refix.main.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class PartUse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Part part;

    @ManyToOne
    @JsonIgnore
    private ServiceOrder serviceOrder;
    private int quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PartUse(Part part, ServiceOrder serviceOrder) {
        this.part = part;
        this.serviceOrder = serviceOrder;
        this.quantity = 1;
    }

    public PartUse() {
    }

    @Override
    public String toString() {
        return "PartUse{" +
                "id=" + id +
                ", part=" + part +
                ", serviceOrder=" + serviceOrder +
                ", quantity=" + quantity +
                '}';
    }
}
