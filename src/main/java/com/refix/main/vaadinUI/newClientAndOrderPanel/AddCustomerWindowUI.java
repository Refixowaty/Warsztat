package com.refix.main.vaadinUI.newClientAndOrderPanel;


import com.refix.main.entity.Customer;
import com.refix.main.service.CarService;
import com.refix.main.service.CustomerService;
import com.refix.main.service.ServiceOrderService;
import com.refix.main.service.WorkerService;
import com.refix.main.utils.validation.ExceptionThrower;
import com.vaadin.ui.*;


public class AddCustomerWindowUI extends Window{
        private CustomerService custServ;
        private CarService carServ;
        private ServiceOrderService serviceOrderServ;
        private WorkerService workerServ;

        private TextField nameField, lastNameField, phoneNumberField, emailField;
        private TextField companyNameField, nipField, regonField;
        private TextArea addressArea;
        private RadioButtonGroup<String> kindOfClientRadioButton;
        private Button addClientButton, cancelButton;

        private VerticalLayout clientFieldsVerticalLayout, businessClientFieldsVerticalLayout, mainCointainer;
        private HorizontalLayout fieldsHorizontalLayout, buttonsHorizontalLayout;

        public AddCustomerWindowUI(CustomerService custServ, CarService carServ,
                                   ServiceOrderService serviceOrderServ, WorkerService workerServ){
                super("Add Client:");
                this.custServ = custServ;
                this.carServ = carServ;
                this.serviceOrderServ = serviceOrderServ;
                this.workerServ = workerServ;
                center();
                setModal(true);
                setClosable(false);
                setResizable(false);

                nameField = new TextField("Client name: ");
                lastNameField = new TextField("Client lastname: ");
                phoneNumberField = new TextField("Phone numer:");
                emailField = new TextField("E-mail: ");

                kindOfClientRadioButton = new RadioButtonGroup<>();
                                kindOfClientRadioButton.setItems("Individual client", "Business client");
                                kindOfClientRadioButton.setDescription("Choose kind of client");
                                kindOfClientRadioButton.addSelectionListener(event -> radioButtonGroupListener());

                companyNameField = new TextField("Company: ");
                addressArea = new TextArea("Address: ");
                nipField = new TextField("Nip: ");
                regonField = new TextField("Regon: ");
                disableBusinessFields();

                addClientButton = new Button("Add client and next", clickEvent -> validateFieldsAndAddCustomer());
                                addClientButton.setWidth(260, Unit.PIXELS);
                cancelButton = new Button("Canel", clickEvent -> close());
                                cancelButton.setWidth(260, Unit.PIXELS);

                clientFieldsVerticalLayout = new VerticalLayout();
                businessClientFieldsVerticalLayout = new VerticalLayout();
                fieldsHorizontalLayout = new HorizontalLayout();
                buttonsHorizontalLayout = new HorizontalLayout();
                mainCointainer = new VerticalLayout();

                clientFieldsVerticalLayout.addComponents(nameField, lastNameField, phoneNumberField, emailField, kindOfClientRadioButton);
                businessClientFieldsVerticalLayout.addComponents(companyNameField, addressArea, nipField, regonField);
                fieldsHorizontalLayout.addComponents(clientFieldsVerticalLayout, businessClientFieldsVerticalLayout);
                buttonsHorizontalLayout.addComponents(cancelButton, addClientButton);
                mainCointainer.addComponents(fieldsHorizontalLayout, buttonsHorizontalLayout);

                setContent(mainCointainer);
        }

        private void radioButtonGroupListener(){
                if(kindOfClientRadioButton.getValue() == "Individual client") {
                        disableBusinessFields();
                        clearBusinessFields();
                }
                else if(kindOfClientRadioButton.getValue() == "Business client") {
                        enableBusinessFields();
                }
        }

        private void validateFieldsAndAddCustomer(){
                try {
                        checkRadioButtonSelect();
                        if (kindOfClientRadioButton.getValue() == "Individual client") {
                                try {
                                        validateCustomerData();
                                } catch (Exception e) {
                                        Notification.show(e.getMessage());
                                        return;
                                }
                                addCustomerAndOpenNextWindow();
                        } else if (kindOfClientRadioButton.getValue() == "Business client") {
                                try {
                                        validateBusinessCustomerData();
                                } catch (Exception e) {
                                        Notification.show(e.getMessage());
                                        return;
                                }
                                addBusinessCustomerAndOpenNextWindow();
                        }
                }catch(Exception e){
                        Notification.show("Please choose kind of Client");
                        return;
                }
        }

        private void checkRadioButtonSelect() throws Exception{
                ExceptionThrower.checkIfHasAnySelectedRadioButton(kindOfClientRadioButton);
        }


        private void validateCustomerData() throws Exception {
                ExceptionThrower.checkNamesFormat(nameField, lastNameField);
                ExceptionThrower.checkIfFieldsAreNotEmpty(nameField, lastNameField, emailField, phoneNumberField);
                ExceptionThrower.checkPhoneNumber(phoneNumberField);
                ExceptionThrower.checkEmail(emailField);
                ExceptionThrower.checkIfHasAnySelectedRadioButton(kindOfClientRadioButton);
        }

        private void validateBusinessCustomerData() throws Exception {
                ExceptionThrower.checkNamesFormat(nameField, lastNameField);
                ExceptionThrower.checkIfFieldsAreNotEmpty(nameField, lastNameField, emailField, phoneNumberField, companyNameField, nipField, regonField);
                ExceptionThrower.checkIfAreaIsNotEmpty(addressArea);
                ExceptionThrower.checkPhoneNumber(phoneNumberField);
                ExceptionThrower.checkEmail(emailField);
                ExceptionThrower.checkIfHasAnySelectedRadioButton(kindOfClientRadioButton);
        }


        private void addCustomerAndOpenNextWindow(){
                Customer customer = custServ.createCustomer(nameField.getValue(), lastNameField.getValue(),
                        phoneNumberField.getValue(), emailField.getValue(), false);
                openNextWindowForCustomer(customer);
        }

        private void addBusinessCustomerAndOpenNextWindow(){
                Customer customer = custServ.createBusinessCustomer(nameField.getValue(), lastNameField.getValue(),
                        phoneNumberField.getValue(), emailField.getValue(), companyNameField.getValue(), addressArea.getValue(),
                        nipField.getValue(), regonField.getValue(), true);
                openNextWindowForCustomer(customer);
        }

        private void openNextWindowForCustomer(Customer customer){
                AddCustomerCarsWindowUI open =
                        new AddCustomerCarsWindowUI(customer, custServ, carServ, serviceOrderServ, workerServ);
                UI.getCurrent().addWindow(open);
                close();
        }

        private void disableBusinessFields(){
                companyNameField.setEnabled(false);
                addressArea.setEnabled(false);
                nipField.setEnabled(false);
                regonField.setEnabled(false);
        }

        private void enableBusinessFields(){
                companyNameField.setEnabled(true);
                addressArea.setEnabled(true);
                nipField.setEnabled(true);
                regonField.setEnabled(true);
        }

        private void clearBusinessFields(){
                companyNameField.clear();
                addressArea.clear();
                nipField.clear();
                regonField.clear();
        }
}
