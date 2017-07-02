package com.refix.main.vaadinUI.clientsPanel.managePanels;


import com.refix.main.entity.Customer;
import com.refix.main.service.CustomerService;
import com.vaadin.ui.*;

public class AreYouSureDeleteClientUI extends Window {

    private CustomerService custServ;

    private Customer customer;

    private Button cancelButton, deleteClientButton;
    private Label infoLabel;

    private HorizontalLayout infoHorizontalLayout, buttonsHorizontalLayout;
    private VerticalLayout mainContainer;

    public AreYouSureDeleteClientUI(Customer customer, CustomerService custServ){
        super("Delete client: " + customer.getName() + " " + customer.getLastName() + " ?");
        this.customer = customer;
        this.custServ = custServ;
        center();
        setModal(true);
        setClosable(false);
        setResizable(false);

        infoLabel = new Label("Are you sure? This operation is irreversible.");

        cancelButton = new Button("Cancel", clickEvent -> close());
                cancelButton.setWidth(150, Unit.PIXELS);
        deleteClientButton = new Button("Delete Client", clickEvent -> deleteClient());
                deleteClientButton.setWidth(150, Unit.PIXELS);

        infoHorizontalLayout = new HorizontalLayout();
        buttonsHorizontalLayout = new HorizontalLayout();
        mainContainer = new VerticalLayout();

        infoHorizontalLayout.addComponents(infoLabel);
        buttonsHorizontalLayout.addComponents(cancelButton, deleteClientButton);
        mainContainer.addComponents(infoHorizontalLayout, buttonsHorizontalLayout);

        setContent(mainContainer);
    }

    private void deleteClient(){
        custServ.deleteCustomer(customer);
        close();
    }
}
