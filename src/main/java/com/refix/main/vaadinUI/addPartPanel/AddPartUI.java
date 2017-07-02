package com.refix.main.vaadinUI.addPartPanel;


import com.refix.main.service.PartService;
import com.refix.main.utils.Observer;
import com.refix.main.utils.WorkshopUtils;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class AddPartUI extends Window{
    private static List<Observer> observers = new ArrayList<>();

    private PartService partServ;

    private Button addPartButton;
    private TextField partNameField, costField, producerField, categoryField, quantityField;
    private RadioButtonGroup<String> newPartRadioButtonGroup;

    private VerticalLayout verticalPart;

    public AddPartUI(PartService partServ){
        super("Add new part: ");
        this.partServ = partServ;
        center();
        setModal(true);

        categoryField = new TextField("Category: ");
        partNameField = new TextField("Kind of part: ");
        producerField = new TextField("Producer: ");
        costField = new TextField("Value (PLN)");
        newPartRadioButtonGroup = new RadioButtonGroup<>();
                newPartRadioButtonGroup.setCaption("New?");
                newPartRadioButtonGroup.setItems("New", "Used");
        quantityField = new TextField("Quantity: ");
        addPartButton = new Button("Add part", clickEvent -> addPartIfValidated());

        verticalPart = new VerticalLayout();

        verticalPart.addComponents(categoryField,partNameField,producerField,costField,
                newPartRadioButtonGroup,quantityField,addPartButton);

        setContent(verticalPart);
    }

    private void addPartIfValidated(){
        if (fieldsAreValidated())
            addPart();
    }

    private boolean fieldsAreValidated(){
        return true;
    }

    public void addPart(){
        partServ.createPart(partNameField.getValue(), Integer.parseInt(costField.getValue()),producerField.getValue(),
                categoryField.getValue(), WorkshopUtils.convertPartValueToBoolean(newPartRadioButtonGroup.getValue()),
                Integer.parseInt(quantityField.getValue()));

        notifyObservers();
        close();
    }

    public static void registerObserver(Observer observer){
        observers.add(observer);
    }

    private void notifyObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
