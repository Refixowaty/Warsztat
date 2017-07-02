package com.refix.main.vaadinUI.clientsPanel.managePanels;


import com.refix.main.entity.Car;
import com.refix.main.entity.ServiceOrder;
import com.refix.main.service.CarService;
import com.refix.main.service.PartService;
import com.refix.main.service.ServiceOrderService;
import com.refix.main.service.WorkerService;
import com.refix.main.utils.Observer;
import com.refix.main.utils.WorkshopUtils;
import com.refix.main.vaadinUI.manageOrderPanel.ManageOrderPanelUI;
import com.refix.main.vaadinUI.manageOrderPanel.editChangeFinalizePanels.AreYouSureWindowUI;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShowAllOrdersUI extends Window implements Observer{

    private static List<Observer> observers = new ArrayList<>();

    private ServiceOrderService serviceOrderServ;
    private CarService carServ;
    private PartService partServ;
    private WorkerService workerServ;

    private Car car;

    private Grid<ServiceOrder> carServiceOrdersGrid;
    private Button addOrderButton, manageOrderPanelButton, cancelButton;

    private VerticalLayout mainContainer;
    private HorizontalLayout buttonsHorizontalLayout;
    private VerticalLayout gridVerticalLayout;


    public ShowAllOrdersUI(Car car, ServiceOrderService serviceOrderServ, CarService carServ, PartService partServ, WorkerService workerServ) {
        super("Car ID: " + car.getId() + ", Owner: " + car.getOwner().getName() + " " + car.getOwner().getLastName());
        this.car = car;
        this.serviceOrderServ = serviceOrderServ;
        this.carServ = carServ;
        this.partServ = partServ;
        this.workerServ = workerServ;
        center();
        setModal(true);
        setWidth(1450,Unit.PIXELS);

        carServiceOrdersGrid = new Grid<>();
        carServiceOrdersGrid.setWidth(1350,Unit.PIXELS);
        carServiceOrdersGrid.setCaption("Car: " + car.getBrand() + " " + car.getModel()
                + " " + car.getRegNum());
        carServiceOrdersGrid.addColumn(ServiceOrder::getId).setCaption("Order ID: ");
        carServiceOrdersGrid.addColumn(serviceOrder -> WorkshopUtils.showCustomerNameLastName(serviceOrder.getCar().getOwner())).setCaption("Owner:");
        carServiceOrdersGrid.addColumn(ServiceOrder::getDescription).setCaption("Description: ");
        carServiceOrdersGrid.addColumn(ServiceOrder::getDate).setCaption("Date: ");
        carServiceOrdersGrid.addColumn(ServiceOrder::getCost).setCaption("Cost (of finalized order)");
        carServiceOrdersGrid.addColumn(ServiceOrder::getWorker).setCaption("Worker: ");
        carServiceOrdersGrid.addColumn(ServiceOrder::isFinished).setCaption("Finished: ");
        carServiceOrdersGrid.setItems(car.getServiceOrders());
        carServiceOrdersGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        addOrderButton = new Button("Add new order for " + car.getBrand() + " " + car.getModel() + " " + car.getRegNum(), clickEvent -> addNewOrderForCar());
                addOrderButton.setWidth(466, Unit.PIXELS);
        manageOrderPanelButton = new Button("Manage order of " + car.getBrand() + " " + car.getModel() + " " + car.getRegNum(), clickEvent -> showManageOrderPanel());
                manageOrderPanelButton.setWidth(466, Unit.PIXELS);
        cancelButton = new Button("Cancel", clickEvent -> cancel());
                cancelButton.setWidth(466, Unit.PIXELS);
        mainContainer = new VerticalLayout();
        gridVerticalLayout = new VerticalLayout();
        buttonsHorizontalLayout = new HorizontalLayout();

        gridVerticalLayout.addComponents(carServiceOrdersGrid);
        buttonsHorizontalLayout.addComponents(addOrderButton, manageOrderPanelButton, cancelButton);
        mainContainer.addComponents(gridVerticalLayout, buttonsHorizontalLayout);

        setContent(mainContainer);

        AreYouSureWindowUI.registerObserver(this);
    }

    private void addNewOrderForCar(){

    }

    private void showManageOrderPanel(){
        ServiceOrder serviceOrder = carServiceOrdersGrid.getSelectedItems().stream().collect(Collectors.toList()).get(0);
        ManageOrderPanelUI updateOrderPanelUI = new ManageOrderPanelUI(serviceOrder, serviceOrderServ, partServ, workerServ);
        UI.getCurrent().addWindow(updateOrderPanelUI);

    }

    private void cancel(){
        close();
        notifyObservers();
    }

    private void notifyObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public static void registerObserver(Observer observer){
        observers.add(observer);
    }


    @Override
    public void update() {
        carServiceOrdersGrid.setItems(car.getServiceOrders());
    }


}
