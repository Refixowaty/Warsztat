package com.refix.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vaadin.ui.Image;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Worker{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String lastName;
    private String workPosition;
    private String phoneNumber;
    private String pesel;
    private String bankAccountNumber;
    private String street;
    private String zipCode;
    private String city;
    @JsonIgnore
    private Image photo;
    private boolean employed;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public boolean isEmployed() {
        return employed;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public Worker() {
    }

    public Worker(String name, String lastName, String workPosition, String phoneNumber, String pesel) {
        this.name = name;
        this.lastName = lastName;
        this.workPosition = workPosition;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
        this.employed = true;
    }

    public Worker(String name, String lastName, String workPosition, String phoneNumber,
                  String pesel, String bankAccountNumber, String street, String zipCode, String city) {
        this(name, lastName, workPosition, phoneNumber, pesel);
        this.bankAccountNumber = bankAccountNumber;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.employed = true;
    }

    public Worker(String name, String lastName, String workPosition, String phoneNumber,
                  String pesel, String bankAccountNumber, String street, String zipCode, String city, Image photo) {
        this(name, lastName, workPosition, phoneNumber, pesel);
        this.bankAccountNumber = bankAccountNumber;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.photo = photo;
        this.employed = true;
    }

    @Override
    public String toString() {
        return name + " " + lastName + ", " + workPosition;
    }
}
