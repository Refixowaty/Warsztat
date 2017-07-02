package com.refix.main.vaadinUI.addPartPanel;


import com.refix.main.entity.Part;
import com.refix.main.service.PartService;
import com.refix.main.utils.Observer;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.ArrayList;
import java.util.List;

public class UpdatePartQuantityUI extends Window {
    private static List<Observer> observers = new ArrayList<>();
    private PartService partServ;

    private long id;

    private TextField quantityField;
    private Button confirmButton;

    private VerticalLayout verticalLayout;

    public UpdatePartQuantityUI(long id, PartService partServ){
        super("Add new part: ");
        this.partServ = partServ;
        this.id = id;
        center();
        setModal(true);

        quantityField = new TextField("Add quantity: ");
        confirmButton = new Button("Confrim", this::addQuantity);

        verticalLayout = new VerticalLayout();

        verticalLayout.addComponents(quantityField,confirmButton);

        setContent(verticalLayout);
    }


    public void addQuantity(Button.ClickEvent clickEvent){
        Part part = partServ.readPart(id);
        int quantity = part.getQuantity();
        partServ.updatePartQuantity(id, quantity + Integer.parseInt(quantityField.getValue()));
        notifyObservers();
        close();
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
