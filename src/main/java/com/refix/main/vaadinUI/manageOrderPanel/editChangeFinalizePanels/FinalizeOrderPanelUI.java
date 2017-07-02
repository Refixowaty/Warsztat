package com.refix.main.vaadinUI.manageOrderPanel.editChangeFinalizePanels;


import com.refix.main.entity.PartUse;
import com.refix.main.entity.ServiceOrder;
import com.refix.main.service.PartService;
import com.refix.main.service.ServiceOrderService;
import com.refix.main.service.WorkerService;
import com.refix.main.utils.validation.ExceptionThrower;
import com.refix.main.vaadinUI.clientsPanel.managePanels.ShowAllOrdersUI;
import com.vaadin.ui.*;

import java.util.List;

public class FinalizeOrderPanelUI extends Window{

    private ServiceOrderService serviceOrderServ;
    private PartService partServ;
    private WorkerService workerServ;

    private ServiceOrder serviceOrder;

    private double finalCost;

    private List<PartUse> actualPartsList;

    private TextField costOfServiceField, finalCostField;
    private Label plusLabel, costOfUsedPartsLabel, typeCostLabel, percentlabel, separatorLabel, separator2Label;
    private ComboBox<Double> profitMarginComboBox;
    private Button cancelButton, finalizeOrderButton, calculateButton;

    private HorizontalLayout calculatePanelHorizontalLayout, buttonsHorizontalLayout, resultHorizontalLayout, separatorHorizontalLayout, separator2HorizontalLayout;
    private VerticalLayout mainContainer;

    public FinalizeOrderPanelUI(ServiceOrderService serviceOrderServ, PartService partServ, ServiceOrder serviceOrder,
                                List<PartUse> actualPartsList){
        super("Calculate and finalize order: ");
        this.serviceOrderServ = serviceOrderServ;
        this.partServ = partServ;
        this.serviceOrder = serviceOrder;
        this.actualPartsList = actualPartsList;
        center();
        setModal(true);

        typeCostLabel = new Label("Type cost of service: ");
        costOfServiceField = new TextField();
        plusLabel = new Label("+");
        costOfUsedPartsLabel = new Label(calculateCostOfUsedPartsToLabel() + " PLN   +  ");
        profitMarginComboBox = new ComboBox<>();
                profitMarginComboBox.setItems(5.0, 10.0, 15.0, 20.0, 22.5);
                profitMarginComboBox.setWidth(120,Unit.PIXELS);
        percentlabel = new Label(" %");
        separator2Label = new Label("..................................................................................................................................................................");
        calculateButton = new Button("Calculate cost of order", clickEvent -> validateDataToCalculateOrder());
                calculateButton.setWidth(420, Unit.PIXELS);
        finalCostField = new TextField();
                finalCostField.setReadOnly(true);
        separatorLabel = new Label("..................................................................................................................................................................");
        cancelButton = new Button("Cancel", clickEvent -> cancel());
                cancelButton.setWidth(186, Unit.PIXELS);
        finalizeOrderButton = new Button("Finalize Order", clickEvent -> validateDataToFinalizeOrder());
                finalizeOrderButton.setWidth(420, Unit.PIXELS);

        calculatePanelHorizontalLayout = new HorizontalLayout();
        buttonsHorizontalLayout = new HorizontalLayout();
        separator2HorizontalLayout = new HorizontalLayout();
        resultHorizontalLayout = new HorizontalLayout();
        separatorHorizontalLayout = new HorizontalLayout();
        mainContainer = new VerticalLayout();

        calculatePanelHorizontalLayout.addComponents(typeCostLabel,costOfServiceField,plusLabel,costOfUsedPartsLabel,profitMarginComboBox, percentlabel);
        separator2HorizontalLayout.addComponents(separatorLabel);
        buttonsHorizontalLayout.addComponents(cancelButton, finalizeOrderButton);
        separatorHorizontalLayout.addComponents(separator2Label);
        resultHorizontalLayout.addComponents(finalCostField, calculateButton);
        mainContainer.addComponents(calculatePanelHorizontalLayout,separator2HorizontalLayout,resultHorizontalLayout,separatorHorizontalLayout,buttonsHorizontalLayout);

        setContent(mainContainer);

    }

    private String calculateCostOfUsedPartsToLabel(){
        double cost = partServ.calculatePartsCostInServiceOrder(actualPartsList);
        return String.valueOf(cost);
    }

    private double calculateCostOfUsedPartsToFinalize(){
        double cost = partServ.calculatePartsCostInServiceOrder(actualPartsList);
        return cost;
    }

    private void calculateCostOfOrder(){
        double orderCost = Double.parseDouble(costOfServiceField.getValue());
        double partsCost = calculateCostOfUsedPartsToFinalize();
        double partsProfitMargin = profitMarginComboBox.getValue();

        double result = orderCost + partsCost + (partsCost * partsProfitMargin * 0.01);

        finalCostField.setValue(String.valueOf(result) + " PLN");
        finalCost = result;
    }

    private void openAreYouSureWindow(){
        AreYouSureWindowUI open = new AreYouSureWindowUI(serviceOrderServ, serviceOrder, finalCost);
        UI.getCurrent().addWindow(open);
        close();
    }

    private void cancel(){
        close();
    }



    //validation
    private void validateDataToCalculateOrder(){
        try {
            validateFieldsToCalculate();
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            Notification.show(errorMessage);
            return;
        }
        calculateCostOfOrder();
    }

    private void validateFieldsToCalculate() throws Exception {
        ExceptionThrower.checkIfFieldsAreNotEmpty(costOfServiceField);
        ExceptionThrower.checkIfHasAnyChosen(profitMarginComboBox);
        ExceptionThrower.checkCostFormat(costOfServiceField);
    }

    private void validateDataToFinalizeOrder(){
        try {
            validateFinalCost();
        } catch (Exception e) {
            Notification.show("Calculate cost first!");
            return;
        }
        openAreYouSureWindow();
    }

    private void validateFinalCost() throws Exception {
        ExceptionThrower.checkIfFieldsAreNotEmpty(finalCostField);
    }
}
