package com.refix.main.vaadinUI.clientsPanel;

import com.refix.main.entity.Car;
import com.refix.main.entity.Customer;
import com.refix.main.service.*;
import com.refix.main.utils.Filter;
import com.refix.main.utils.Observer;
import com.refix.main.vaadinUI.clientsPanel.managePanels.AddCarForExistingClient;
import com.refix.main.vaadinUI.clientsPanel.managePanels.AddClientUI;
import com.refix.main.vaadinUI.clientsPanel.managePanels.AreYouSureDeleteClientUI;
import com.refix.main.vaadinUI.clientsPanel.managePanels.ShowAllOrdersUI;
import com.vaadin.event.selection.MultiSelectionEvent;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ClientPanelUI extends Window implements Observer{

    private CustomerService custServ;
    private ServiceOrderService serviceOrderServ;
    private CarService carServ;
    private WorkerService workerServ;
    private PartService partServ;

    private Customer customer;
    private Car car;

    private List<Car> carsList;
    private List<Customer> individualClients;
    private List<Customer> businessClients;
    private List<Customer> allClients;

    private Grid<Customer> customerGrid;
    private ListSelect<Car> customerCarsListSelect;
    private Button addCarButton, showCarOrdersButton, closeButton;
    private Button addClientButton, deleteClientButton, updateClientDataButton, showAllDataOfClient;
    private Label separatorLabel;
    private TextField searchField;
    private CheckBoxGroup<String> kindOfClientCheckBoxGroup;

    private HorizontalLayout mainHorizontalLayout, bottomButtonsHorizontalLayout, topSearchHorizontalLayout;
    private VerticalLayout mainContainer, leftPartVerticalLayout, rightPartVerticalLayout;

    public ClientPanelUI(CustomerService custServ, ServiceOrderService serviceOrderServ,
                         CarService carServ, WorkerService workerServ, PartService partServ) {
        super("Client's panel:");
        this.custServ = custServ;
        this.serviceOrderServ = serviceOrderServ;
        this.carServ = carServ;
        this.workerServ = workerServ;
        this.partServ = partServ;
        center();
        setModal(true);
        setClosable(false);
        setResizable(false);

        separatorLabel = new Label("...................................................................................................................................");
        searchField = new TextField("Filter by name or lastname:");
                searchField.setWidth(500, Unit.PIXELS);
        kindOfClientCheckBoxGroup = new CheckBoxGroup<>();
                kindOfClientCheckBoxGroup.setItems("Individual client", "Business client");
                kindOfClientCheckBoxGroup.setDescription("Choose kind of client");
                kindOfClientCheckBoxGroup.addSelectionListener(listener -> checkBoxGroupListener());


        carsList = new ArrayList<>();
        individualClients = new ArrayList<>();
        businessClients = new ArrayList<>();
        allClients = new ArrayList<>();
                allClients.addAll(custServ.readAllCustomers());

        addCarButton = new Button("Add car ", clickEvent -> addCarForSelectedClient());
                addCarButton.setWidth(500, Unit.PIXELS);
        showCarOrdersButton = new Button("Show orders", clickEvent -> showCarServiceOrders());
                showCarOrdersButton.setWidth(500, Unit.PIXELS);
        closeButton = new Button("Close", clickEvent -> close());
                closeButton.setWidth(500, Unit.PIXELS);

        addClientButton = new Button("Add Client", clickEvent -> addClient());
                addClientButton.setWidth(166, Unit.PIXELS);
        deleteClientButton = new Button("Delete Client", clickEvent -> deleteClient());
                deleteClientButton.setWidth(166, Unit.PIXELS);
        updateClientDataButton = new Button("Update client data");
                updateClientDataButton.setWidth(165, Unit.PIXELS);
        showAllDataOfClient = new Button("Show more");
                showAllDataOfClient.setWidth(166, Unit.PIXELS);

        customerGrid = new Grid<>();
                customerGrid.setCaption("Choose kind of client");
                customerGrid.setWidth(700, Unit.PIXELS);
                createColumnsForAllClients();
                customerGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
                customerGrid.setItems(allClients);
                customerGrid.addSelectionListener(this::customerGridListener);

        customerCarsListSelect = new ListSelect<>();
                customerCarsListSelect.setCaption("Customer cars: ");
                customerCarsListSelect.setWidth(500, Unit.PIXELS);
                customerCarsListSelect.setHeight(300, Unit.PIXELS);
                customerCarsListSelect.setItems(carsList);
                customerCarsListSelect.addSelectionListener(this::carListListener);

        configureFiltering();

        topSearchHorizontalLayout = new HorizontalLayout();
        leftPartVerticalLayout = new VerticalLayout();
        rightPartVerticalLayout = new VerticalLayout();
        mainHorizontalLayout = new HorizontalLayout();
        bottomButtonsHorizontalLayout = new HorizontalLayout();
        mainContainer = new VerticalLayout();

        topSearchHorizontalLayout.addComponents(searchField, kindOfClientCheckBoxGroup);
        bottomButtonsHorizontalLayout.addComponents(addClientButton, deleteClientButton, updateClientDataButton, showAllDataOfClient);
        leftPartVerticalLayout.addComponents(topSearchHorizontalLayout, customerGrid, bottomButtonsHorizontalLayout);
        rightPartVerticalLayout.addComponents(customerCarsListSelect, addCarButton, showCarOrdersButton, separatorLabel, closeButton);

        mainHorizontalLayout.addComponents(leftPartVerticalLayout,rightPartVerticalLayout);
        mainContainer.addComponents(mainHorizontalLayout);

        setContent(mainContainer);

        AddCarForExistingClient.registerObserver(this);
        ShowAllOrdersUI.registerObserver(this);
        AddClientUI.registerObserver(this);
    }

    private void deleteClient(){
        Customer customer = customerGrid.getSelectedItems().stream().collect(Collectors.toList()).get(0);
        AreYouSureDeleteClientUI open = new AreYouSureDeleteClientUI(customer, custServ);
        UI.getCurrent().addWindow(open);
    }

    private void checkBoxGroupListener(){
        if(kindOfClientCheckBoxGroup.getSelectedItems().size() == 1) {
            if (kindOfClientCheckBoxGroup.isSelected("Individual client")) {
                setIndividualClients();
            }
            else if(kindOfClientCheckBoxGroup.isSelected("Business client")) {
                setBusinessClients();
            }
        }
        if(kindOfClientCheckBoxGroup.getSelectedItems().size() == 0 || kindOfClientCheckBoxGroup.getSelectedItems().size() == 2){
            setAllClients();
        }
    }

    private void setIndividualClients(){
        individualClients.clear();
        customerGrid.removeAllColumns();
        customerGrid.setCaption(kindOfClientCheckBoxGroup.getValue() + "'s");
        createColumnsForIndividualClients();
        individualClients = custServ.findAllClientsByKind(false);
        customerGrid.setItems(individualClients);
    }

    private void setBusinessClients(){
        businessClients.clear();
        customerGrid.removeAllColumns();
        customerGrid.setCaption(kindOfClientCheckBoxGroup.getValue() + "'s");
        createColumnsForBusinessClients();
        businessClients = custServ.findAllClientsByKind(true);
        customerGrid.setItems(businessClients);
    }

    private void setAllClients(){
        allClients.clear();
        customerGrid.removeAllColumns();
        createColumnsForAllClients();
        customerGrid.setCaption("List of all Clients: ");
        allClients = custServ.readAllCustomers();
        customerGrid.setItems(allClients);
    }

    private void createColumnsForAllClients(){
        customerGrid.addColumn(Customer::getId).setCaption("Client ID");
        customerGrid.addColumn(Customer::getCompany).setCaption("Company: ");
        customerGrid.addColumn(Customer::getName).setCaption("Name: ");
        customerGrid.addColumn(Customer::getLastName).setCaption("Lastname: ");
        customerGrid.addColumn(Customer::getPhoneNumber).setCaption("Phone number: ");
        customerGrid.addColumn(Customer::getEmail).setCaption("E-mail: ");
        customerGrid.addColumn(Customer::getNip).setCaption("NIP:");
        customerGrid.addColumn(Customer::getNip).setCaption("REGON:");
    }

    private void createColumnsForBusinessClients(){
        customerGrid.addColumn(Customer::getId).setCaption("Client ID");
        customerGrid.addColumn(Customer::getCompany).setCaption("Company: ");
        customerGrid.addColumn(Customer::getName).setCaption("Name: ");
        customerGrid.addColumn(Customer::getLastName).setCaption("Lastname: ");
        customerGrid.addColumn(Customer::getPhoneNumber).setCaption("Phone number: ");
        customerGrid.addColumn(Customer::getEmail).setCaption("E-mail: ");
    }

    private void createColumnsForIndividualClients(){
        customerGrid.addColumn(Customer::getId).setCaption("Client ID");
        customerGrid.addColumn(Customer::getName).setCaption("Name: ");
        customerGrid.addColumn(Customer::getLastName).setCaption("Lastname: ");
        customerGrid.addColumn(Customer::getPhoneNumber).setCaption("Phone number: ");
        customerGrid.addColumn(Customer::getEmail).setCaption("E-mail: ");
    }

    private void addClient(){
        AddClientUI open = new AddClientUI(custServ);
        UI.getCurrent().addWindow(open);
    }

    private void addCarForSelectedClient(){
        AddCarForExistingClient open = new AddCarForExistingClient(customer, custServ, carServ);
        UI.getCurrent().addWindow(open);
    }

    private void showCarServiceOrders(){
        ShowAllOrdersUI open = new ShowAllOrdersUI(car, serviceOrderServ, carServ, partServ, workerServ);
        UI.getCurrent().addWindow(open);
        setVisible(false);
    }

    private void customerGridListener(SelectionEvent<Customer> event){
        try {
            customer = event.getAllSelectedItems().stream().collect(Collectors.toList()).get(0);
        }catch(Exception e){
            customerGrid.deselectAll();
        }
        updateCarsSelect();
    }

    private void updateCarsSelect(){
        carsList = carServ.findAllCustomerCars(customer.getId());
        customerCarsListSelect.setItems(carsList);
        customerCarsListSelect.setCaption("Client: " + customer.getName() + " " + customer.getLastName());
        addCarButton.setCaption("Add car for : " + customer.getName() + " " + customer.getLastName());
        showCarOrdersButton.setCaption("Show orders");
    }

    private void carListListener(MultiSelectionEvent<Car> event){
        try {
            car = event.getAllSelectedItems().stream().collect(Collectors.toList()).get(0);
        }catch(Exception e) {
            customerCarsListSelect.deselectAll();
        }
        showCarOrdersButton.setCaption("Show orders for: " + car.getBrand() + " " + car.getModel());
    }

    private void configureFiltering(){
        searchField.addValueChangeListener(text -> filterCustomersGrid());
    }

    private void filterCustomersGrid(){

        List<Customer> customers = new ArrayList<>();

        if(kindOfClientCheckBoxGroup.getSelectedItems().size() == 1) {
            if (kindOfClientCheckBoxGroup.isSelected("Individual client")) {
                customers = custServ.findAllClientsByKind(false);
            }
            else if(kindOfClientCheckBoxGroup.isSelected("Business client")) {
                customers = custServ.findAllClientsByKind(true);
            }
        }
        if(kindOfClientCheckBoxGroup.getSelectedItems().size() == 0 || kindOfClientCheckBoxGroup.getSelectedItems().size() == 2){
            customers = custServ.readAllCustomers();
        }

        if (searchField.isEmpty())
            customerGrid.setItems(customers);
        else {
            String fullname = searchField.getValue();
            List<Customer> filteredCustomers = customers.stream()
                    .filter(customer -> Filter.customerNameAndLastNameFilter(customer, fullname))
                    .collect(Collectors.toList());
            customerGrid.setItems(filteredCustomers);
        }
    }

    @Override
    public void update() {
        checkBoxGroupListener();
        updateCarsSelect();
        setVisible(true);
    }
}
