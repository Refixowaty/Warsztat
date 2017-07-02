package com.refix.main.vaadinUI.manageOrderPanel.editChangeFinalizePanels;


import com.refix.main.entity.ServiceOrder;
import com.refix.main.service.ServiceOrderService;
import com.refix.main.utils.Observer;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class EditDescriptionPanelUI extends Window{
    private static List<Observer> observers = new ArrayList<>();

    private ServiceOrderService serviceOrderServ;

    private ServiceOrder serviceOrder;

    private TextArea descriptionArea;

    private Button confirmButton, cancelButton;

    private VerticalLayout mainContainer;
    private HorizontalLayout buttonsHorizontalLayout;

    public EditDescriptionPanelUI(ServiceOrderService serviceOrderServ, ServiceOrder serviceOrder){
        super("Edit description");
        this.serviceOrderServ = serviceOrderServ;
        this.serviceOrder = serviceOrder;
        center();
        setModal(true);

        descriptionArea = new TextArea("Description:");
                descriptionArea.setWidth(300, Unit.PIXELS);
                descriptionArea.setValue(serviceOrder.getDescription());
        confirmButton = new Button("Confirm changes", this::confirmChangeDescription);
                confirmButton.setWidth(205, Unit.PIXELS);
        cancelButton = new Button("Cancel", this::cancel);

        mainContainer = new VerticalLayout();
        buttonsHorizontalLayout = new HorizontalLayout();

        buttonsHorizontalLayout.addComponents(cancelButton, confirmButton);
        mainContainer.addComponents(descriptionArea, buttonsHorizontalLayout);

        setContent(mainContainer);
    }

    private void confirmChangeDescription(Button.ClickEvent clickEvent){
        String description = descriptionArea.getValue();
        serviceOrderServ.updateServiceOrder(serviceOrder, description);
        notifyObservers();
        close();
    }

    private void cancel(Button.ClickEvent clickEvent){
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
