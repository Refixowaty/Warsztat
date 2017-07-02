package com.refix.main.vaadinUI.manageOrderPanel.editChangeFinalizePanels;


import com.refix.main.entity.ServiceOrder;
import com.refix.main.service.ServiceOrderService;
import com.refix.main.utils.Observer;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class AreYouSureWindowUI extends Window{
    private static List<Observer> observers = new ArrayList<>();
    private ServiceOrderService serviceOrderServ;

    private ServiceOrder serviceOrder;

    private double finalCost;

    private Label warrningLabel;
    private Button cancelButton, confirmButton;

    private HorizontalLayout warrningHorizontalLayout, buttonsHorizontalLayout;
    private VerticalLayout mainContainer;

    public AreYouSureWindowUI(ServiceOrderService serviceOrderServ, ServiceOrder serviceOrder, double finalCost){
        super("Finalize order. Are You Sure?");
        this.serviceOrderServ = serviceOrderServ;
        this.serviceOrder = serviceOrder;
        this.finalCost = finalCost;
        center();
        setModal(true);

        warrningLabel = new Label("Are you sure? This operation is irreversible.");
        cancelButton = new Button("Cancel", clickEvent -> cancel());
                cancelButton.setWidth(150,Unit.PIXELS);
        confirmButton = new Button("Finalize order", clickEvent -> finalizeOrder());
                confirmButton.setWidth(150,Unit.PIXELS);

        warrningHorizontalLayout = new HorizontalLayout();
        buttonsHorizontalLayout = new HorizontalLayout();
        mainContainer = new VerticalLayout();

        warrningHorizontalLayout.addComponents(warrningLabel);
        buttonsHorizontalLayout.addComponents(cancelButton,confirmButton);
        mainContainer.addComponents(warrningHorizontalLayout,buttonsHorizontalLayout);

        setContent(mainContainer);
    }

    private void finalizeOrder(){
        serviceOrderServ.updateServiceOrder(serviceOrder, finalCost);
        notifyObservers();
        close();
    }

    private void cancel(){
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
