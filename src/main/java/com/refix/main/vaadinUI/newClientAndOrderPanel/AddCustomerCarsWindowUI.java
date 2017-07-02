package com.refix.main.vaadinUI.newClientAndOrderPanel;

import com.refix.main.entity.Car;
import com.refix.main.entity.Customer;
import com.refix.main.service.CarService;
import com.refix.main.service.CustomerService;
import com.refix.main.service.ServiceOrderService;
import com.refix.main.service.WorkerService;
import com.refix.main.utils.validation.ExceptionThrower;
import com.vaadin.ui.*;

import java.util.stream.Collectors;


public class AddCustomerCarsWindowUI extends Window {

    private CustomerService custServ;
    private CarService carServ;
    private ServiceOrderService serviceOrderServ;
    private WorkerService workerServ;

    private Customer customer;

    private Label numberOfCarsLabel, endOptionsLabel;
    private TextField brandField, modelField, engCapField, regNumField, prodYearField;
    private Button cancelButton, addCarButton, addServiceOrderButton, removeCarButton, finishButton;
    private ListSelect<Car> carListSelect;

    private VerticalLayout addCarFieldsVerticalLayout, leftMainVerticalLayout, rightMainVerticalLayout;
    private HorizontalLayout mainContainerHorizontalLayout;

    public AddCustomerCarsWindowUI(Customer customer, CustomerService custServ, CarService carServ, ServiceOrderService serviceOrderServ, WorkerService workerServ){
        super("ADD CARS" + " (CLIENT ID: " + Long.toString(customer.getId()) + ")");
        this.customer = customer;
        this.custServ = custServ;
        this.carServ = carServ;
        this.serviceOrderServ = serviceOrderServ;
        this.workerServ = workerServ;

        setWidth(900, Unit.PIXELS);
        center();
        setModal(true);
        setClosable(false);
        setResizable(false);

        brandField = new TextField("Car brand:");
        modelField = new TextField("Model:");
        engCapField = new TextField("Engine Capacity:");
        regNumField = new TextField("Registration number: ");
        prodYearField = new TextField("Production Year: ");
        numberOfCarsLabel = new Label();
        carListSelect = new ListSelect<>("Added cars for " + customer.getName() + " " + customer.getLastName());
                carListSelect.setWidth(450, Unit.PIXELS);
                carListSelect.setHeight(150, Unit.PIXELS);
                carListSelect.setItems(carServ.findAllCustomerCars(customer.getId()));
        cancelButton = new Button("Cancel Process", this::cancelProcess);
                cancelButton.setWidth(450, Unit.PIXELS);
        addCarButton = new Button("Add car", clickEvent -> validateFieldsAndAddCar());
                addCarButton.setWidth(185, Unit.PIXELS);
        addServiceOrderButton = new Button("Select car and make/correct or update order", clickEvent -> validateCarSelectAndOpenAddingOrderPanel());
                addServiceOrderButton.setWidth(450, Unit.PIXELS);
        endOptionsLabel = new Label("......................................................................................................................");
        removeCarButton = new Button("Remove selected Car", clickEvent -> validateCarSelectAndRemoveCar());
                removeCarButton.setWidth(450, Unit.PIXELS);
        finishButton = new Button("Finish", this::finishProcess);
                finishButton.setWidth(450, Unit.PIXELS);
        addCarFieldsVerticalLayout = new VerticalLayout();
        leftMainVerticalLayout = new VerticalLayout();
        mainContainerHorizontalLayout = new HorizontalLayout();
        rightMainVerticalLayout = new VerticalLayout();

        addCarFieldsVerticalLayout.addComponents(brandField,modelField,engCapField,regNumField, prodYearField, addCarButton);
        leftMainVerticalLayout.addComponents(addCarFieldsVerticalLayout);
        rightMainVerticalLayout.addComponents(carListSelect, numberOfCarsLabel, addServiceOrderButton,removeCarButton,endOptionsLabel,cancelButton, finishButton);
        mainContainerHorizontalLayout.addComponents(leftMainVerticalLayout, rightMainVerticalLayout);

        setContent(mainContainerHorizontalLayout);

    }

    public void addCar(){
        carServ.createCar(customer, brandField.getValue().toUpperCase(), modelField.getValue().toUpperCase(), engCapField.getValue().toUpperCase(),
                regNumField.getValue().toUpperCase(), prodYearField.getValue());

        numberOfCarsLabel.setValue("You added " + custServ.getCustomerCarAmount(customer.getId())+""
                + " cars for " + customer.getName() + " " + customer.getLastName());

        carListSelect.setItems(carServ.findAllCustomerCars(customer.getId()));

        clearFields();
    }

    private void openAddingOrderPanel(){
        Car car = carListSelect.getSelectedItems().stream().collect(Collectors.toList()).get(0);
        AddOrderForCustomerCarWindowUI addOrderForCustomerCarWindowUI =
                new AddOrderForCustomerCarWindowUI(customer, car, custServ, carServ, serviceOrderServ, workerServ);
        UI.getCurrent().addWindow(addOrderForCustomerCarWindowUI);
    }

    private void removeSelectedCar() {
        Car car = carListSelect.getSelectedItems().stream().collect(Collectors.toList()).get(0);
        long id = car.getId();
        carServ.deleteCar(id);
        carListSelect.setItems(carServ.findAllCustomerCars(customer.getId()));
    }

    private void cancelProcess(Button.ClickEvent clickEvent){
        long id = customer.getId();
        custServ.deleteCustomer(id);
        close();
    }

    private void clearFields(){
        brandField.clear();
        modelField.clear();
        engCapField.clear();
        regNumField.clear();
        prodYearField.clear();
    }

    private void finishProcess(Button.ClickEvent clickEvent){
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
    }

    private void validateFields() throws Exception {
        ExceptionThrower.checkIfFieldsAreNotEmpty(brandField, modelField, engCapField, regNumField, prodYearField);
        ExceptionThrower.checkYear(prodYearField);
    }

    private void validateCarSelectAndOpenAddingOrderPanel(){
        try{
            validateListSelect();
        }catch(Exception e){
            String errorMessage = e.getMessage();
            Notification.show(errorMessage);
            return;
        }
        openAddingOrderPanel();
    }

    private void validateCarSelectAndRemoveCar(){
        try{
            validateListSelect();
        }catch(Exception e){
            String errorMessage = e.getMessage();
            Notification.show(errorMessage);
            return;
        }
        removeSelectedCar();
    }

    private void validateListSelect() throws Exception{
        ExceptionThrower.checkIfHasAnySelected(carListSelect);
    }


}
