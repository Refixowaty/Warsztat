package com.refix.main.vaadinUI;

import com.refix.main.entity.ServiceOrder;
import com.refix.main.service.*;
import com.refix.main.utils.Observer;
import com.refix.main.utils.WorkshopUtils;
import com.refix.main.utils.exceptions.NotSelectedException;
import com.refix.main.utils.validation.Validator;
import com.refix.main.vaadinUI.addPartPanel.PartsPanelUI;
import com.refix.main.vaadinUI.clientsPanel.ClientPanelUI;
import com.refix.main.vaadinUI.employeesPanel.EmployeesPanelUI;
import com.refix.main.vaadinUI.manageOrderPanel.ManageOrderPanelUI;
import com.refix.main.vaadinUI.manageOrderPanel.editChangeFinalizePanels.AreYouSureWindowUI;
import com.refix.main.vaadinUI.newClientAndOrderPanel.AddCustomerWindowUI;
import com.refix.main.vaadinUI.newClientAndOrderPanel.AddOrderForCustomerCarWindowUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@SpringUI
public class MainUI extends UI implements Observer{

    @Autowired
    private CustomerService custServ;
    @Autowired
    private CarService carServ;
    @Autowired
    private ServiceOrderService serviceOrderServ;
    @Autowired
    private WorkerService workerServ;
    @Autowired
    private PartService partServ;

    private Button addNewCustAndOrderButton, workersPanelButton, partsPanelButton, customersPanelButton;
    private Button manageOrderButton;
    private Grid<ServiceOrder> serviceOrderGrid;

    private HorizontalLayout horizontalHeaderContent, horizontalOrderContent, horizontalFootMenu;
    private VerticalLayout mainContainer;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        addNewCustAndOrderButton = new Button("Quick order for new Client", this::openCustomerAndOrderPanel);
                addNewCustAndOrderButton.setWidth(316, Unit.PIXELS);
        workersPanelButton = new Button("Employees panel", clickEvent -> openEmployeesPanel());
                workersPanelButton.setWidth(316, Unit.PIXELS);
        partsPanelButton = new Button("Parts warehouse panel", this::openPartsPanel);
                partsPanelButton.setWidth(316,Unit.PIXELS);
        customersPanelButton = new Button("Clients panel", clickEvent -> openClientsPanel());
                customersPanelButton.setWidth(316,Unit.PIXELS);

        manageOrderButton = new Button("Manage order", clickEvent -> validateSelectAndOpenManageOrder());
                manageOrderButton.setWidth(300, Unit.PIXELS);


        serviceOrderGrid = new Grid<>();
                serviceOrderGrid.setWidth(1300, Unit.PIXELS);
                serviceOrderGrid.setCaption("ACTIVE SERVICE ORDERS (" + serviceOrderServ.readActiveServiceOrders().size() + ")");
                serviceOrderGrid.addColumn(ServiceOrder::getId).setCaption("Order ID: ").setWidth(100);
                serviceOrderGrid.addColumn(serviceOrder -> WorkshopUtils.showCustomerNameLastName(serviceOrder.getCar().getOwner())).setCaption("Owner:");
                serviceOrderGrid.addColumn(ServiceOrder::getCar).setCaption("Car:").setWidth(250);
                serviceOrderGrid.addColumn(ServiceOrder::getDescription).setCaption("Description:").setWidth(340);
                serviceOrderGrid.addColumn(serviceOrder -> WorkshopUtils.getFormattedDate(serviceOrder.getDate())).setCaption("Date:").setWidth(220);
                serviceOrderGrid.addColumn(serviceOrder -> WorkshopUtils.showWorkerNameLastName(serviceOrder.getWorker())).setCaption("Worker:").setWidth(150);
                serviceOrderGrid.setItems(serviceOrderServ.readActiveServiceOrders());


        horizontalHeaderContent = new HorizontalLayout();
        horizontalOrderContent = new HorizontalLayout();
        horizontalFootMenu = new HorizontalLayout();
        mainContainer = new VerticalLayout();

        horizontalHeaderContent.addComponents(addNewCustAndOrderButton,partsPanelButton,workersPanelButton,customersPanelButton);
        horizontalFootMenu.addComponents(manageOrderButton);
        mainContainer.addComponents(horizontalHeaderContent, serviceOrderGrid, horizontalFootMenu);

        setContent(mainContainer);

        //workerServ.createWorker("Edward", "Wiesiniok", "Starszy mechanik", "893434323", "PESEL:89432312");
        //partServ.createPart("Swieca", 20, "InterCars", "Silnik", true, 20);
        //partServ.createPart("Olej", 20, "Oleje SA", "Oleje Silnikowe", true, 20);
        //workerServ.createWorker("Wojciech", "Jedruch","Mlodszy mechanik", "997", "PESEL:12344321");
        AddOrderForCustomerCarWindowUI.registerObserver(this);
        AreYouSureWindowUI.registerObserver(this);
    }

    private void openCustomerAndOrderPanel(Button.ClickEvent clickEvent){
        AddCustomerWindowUI addCustWindow = new AddCustomerWindowUI(custServ, carServ, serviceOrderServ, workerServ);
        addWindow(addCustWindow);
    }

    private void openPartsPanel(Button.ClickEvent clickEvent){
        PartsPanelUI partsPanelUI = new PartsPanelUI(partServ);
        addWindow(partsPanelUI);
    }

    private void openManageOrderPanel(){
        ServiceOrder serviceOrder = serviceOrderGrid.getSelectedItems().stream().collect(Collectors.toList()).get(0);
        ManageOrderPanelUI updateOrderPanelUI = new ManageOrderPanelUI(serviceOrder, serviceOrderServ, partServ, workerServ);
        UI.getCurrent().addWindow(updateOrderPanelUI);
    }

    private void openEmployeesPanel(){
        EmployeesPanelUI open = new EmployeesPanelUI(workerServ);
        UI.getCurrent().addWindow(open);
    }

    private void openClientsPanel(){
        ClientPanelUI open = new ClientPanelUI(custServ, serviceOrderServ, carServ, workerServ, partServ);
        UI.getCurrent().addWindow(open);
    }


    //validate
    private void validateSelectAndOpenManageOrder(){
        try{
            validateGrid();
        }catch(Exception e){
            String errorMessage = e.getMessage();
            Notification.show(errorMessage);
            return;
        }
        openManageOrderPanel();
    }

    private void validateGrid() throws Exception{
        if (!Validator.hasSelected(serviceOrderGrid))
            throw new NotSelectedException();
    }


    //what updates when notifyObservers
    @Override
    public void update() {
        serviceOrderGrid.setItems(serviceOrderServ.readActiveServiceOrders());
    }
}
