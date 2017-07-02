package com.refix.main.vaadinUI.clientsPanel.managePanels;


import com.refix.main.entity.Customer;
import com.refix.main.service.CarService;
import com.refix.main.service.CustomerService;
import com.refix.main.utils.Observer;
import com.refix.main.utils.validation.ExceptionThrower;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class AddCarForExistingClient extends Window{
    private static List<Observer> observers = new ArrayList<>();

    private CustomerService custServ;
    private CarService carServ;

    private Customer customer;

    private TextField brandField, modelField, engCapField, regNumField, prodYearField;
    private Button cancelButton, addCarButton;
    private Label infoLabel;

    private VerticalLayout addCarFieldsVerticalLayout;
    private HorizontalLayout buttonsHorizontalLayout;
    private VerticalLayout mainContainer;

    public AddCarForExistingClient(Customer customer, CustomerService custServ, CarService carServ) {
        super("Add car for: ");
        this.custServ = custServ;
        this.carServ = carServ;
        this.customer = customer;
        center();
        setModal(true);

        infoLabel = new Label("Client: " + customer.getName() + " " + customer.getLastName());
        brandField = new TextField("Car brand:");
        modelField = new TextField("Model:");
        engCapField = new TextField("Engine Capacity:");
        regNumField = new TextField("Registration number: ");
        prodYearField = new TextField("Production Year: ");

        cancelButton = new Button("Cancel", clickEvent -> cancel());
                cancelButton.setWidth(125, Unit.PIXELS);
        addCarButton = new Button("Add car", clickEvent -> validateFieldsAndAddCar());
                addCarButton.setWidth(125, Unit.PIXELS);

        addCarFieldsVerticalLayout = new VerticalLayout();
        buttonsHorizontalLayout = new HorizontalLayout();
        mainContainer = new VerticalLayout();

        addCarFieldsVerticalLayout.addComponents(infoLabel, brandField,modelField,engCapField,regNumField, prodYearField);
        buttonsHorizontalLayout.addComponents(cancelButton,addCarButton);
        mainContainer.addComponents(addCarFieldsVerticalLayout, buttonsHorizontalLayout);

        setContent(mainContainer);
    }

    private void addCar(){
        carServ.createCar(customer, brandField.getValue().toUpperCase(), modelField.getValue().toUpperCase(), engCapField.getValue().toUpperCase(),
                regNumField.getValue().toUpperCase(), prodYearField.getValue());
        close();
    }

    private void cancel(){
        close();
    }

    //validate
    private void validateFieldsAndAddCar(){
        try {
            validateFields();
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            Notification.show(errorMessage);
            return;
        }
        addCar();
        notifyObservers();
    }

    private void validateFields() throws Exception {
        ExceptionThrower.checkIfFieldsAreNotEmpty(brandField, modelField, engCapField, regNumField, prodYearField);
        ExceptionThrower.checkYear(prodYearField);
    }

    private void notifyObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public static void registerObserver(Observer observer){
        observers.add(observer);
    }
}


