package com.refix.main.vaadinUI.newClientAndOrderPanel;

import com.refix.main.entity.Car;
import com.refix.main.entity.Customer;
import com.refix.main.entity.ServiceOrder;
import com.refix.main.entity.Worker;
import com.refix.main.service.CarService;
import com.refix.main.service.CustomerService;
import com.refix.main.service.ServiceOrderService;
import com.refix.main.service.WorkerService;
import com.refix.main.utils.Observer;
import com.refix.main.utils.validation.ExceptionThrower;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddOrderForCustomerCarWindowUI extends Window{

    private static List<Observer> observers = new ArrayList<>();

    private CustomerService custServ;
    private CarService carServ;
    private ServiceOrderService serviceOrderServ;
    private WorkerService workerServ;

    private Car car;
    private Customer customer;
    private ServiceOrder serviceOrder;


    private Label customerLabel;
    private TextArea descriptionArea;
    private Button cancelButton, addOrderButton;
    private ListSelect<Worker> workerListSelect;

    private HorizontalLayout horizontalLayout1;
    private HorizontalLayout horizontalLayout2;
    private VerticalLayout mainContainer;

    public AddOrderForCustomerCarWindowUI(Customer customer, Car car, CustomerService custServ, CarService carServ, ServiceOrderService serviceOrderServ, WorkerService workerServ){
        super("Add order for car:");
        this.customer = customer;
        this.car = car;
        this.custServ = custServ;
        this.carServ = carServ;
        this.serviceOrderServ = serviceOrderServ;
        this.workerServ = workerServ;
        center();
        setModal(true);
        setClosable(false);
        setResizable(false);

        customerLabel = new Label("Client: " + customer.getName() + " " + customer.getLastName());

        descriptionArea = new TextArea(car.toString() + " Description: ");
                descriptionArea.setWidth(400, Unit.PIXELS);
                descriptionArea.setHeight(300, Unit.PIXELS);

        cancelButton = new Button("Cancel", clickEvent -> close());
                cancelButton.setWidth(400,Unit.PIXELS);

        addOrderButton = new Button("Add order for this car", clickEvent -> validateWorkerSelect());
                addOrderButton.setWidth(400,Unit.PIXELS);

        workerListSelect = new ListSelect<>("Select worker for this order:");
                workerListSelect.setWidth(400, Unit.PIXELS);
                workerListSelect.setHeight(300, Unit.PIXELS);
                workerListSelect.setItems(workerServ.readEmployedWorkers());

        horizontalLayout2 = new HorizontalLayout();
        horizontalLayout1 = new HorizontalLayout();
        mainContainer = new VerticalLayout();

        horizontalLayout2.addComponents(workerListSelect,descriptionArea);
        horizontalLayout1.addComponents(cancelButton, addOrderButton);
        mainContainer.addComponents(customerLabel, horizontalLayout2,horizontalLayout1);

        if (orderExists()) {
            List<ServiceOrder> activeOrders = carServ.findAllOrdersForCar(car.getId());
            serviceOrder = activeOrders.get(activeOrders.size()-1);
            fillDescriptionArea();
        }

        setContent(mainContainer);
    }

    private boolean orderExists(){
        List<ServiceOrder> activeOrders = carServ.findAllOrdersForCar(car.getId());
        return !activeOrders.isEmpty();
    }

    private void fillDescriptionArea(){
        String description = serviceOrder.getDescription();
        descriptionArea.setValue(description);
    }

    private void addOrder(){
        Worker worker = workerListSelect.getSelectedItems().stream().collect(Collectors.toList()).get(0);
        String description = descriptionArea.getValue();
        if(orderExists()) {
            serviceOrderServ.updateServiceOrder(serviceOrder, worker, car, description);
        }
        else{
            serviceOrderServ.createServiceOrder(car, description, worker);
        }
        close();
        notifyObservers();
    }


    //validate
    private void validateWorkerSelect(){
        try {
            validateFieldsToCalculate();
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            Notification.show(errorMessage);
            return;
        }
        addOrder();
    }

    private void validateFieldsToCalculate() throws Exception {
        ExceptionThrower.checkIfHasAnySelected(workerListSelect);
        ExceptionThrower.checkIfAreaIsNotEmpty(descriptionArea);
    }

    public static void registerObserver(Observer observer){
        observers.add(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
