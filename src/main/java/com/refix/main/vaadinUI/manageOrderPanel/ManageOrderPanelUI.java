package com.refix.main.vaadinUI.manageOrderPanel;

import com.refix.main.entity.Part;
import com.refix.main.entity.PartUse;
import com.refix.main.entity.ServiceOrder;
import com.refix.main.service.PartService;
import com.refix.main.service.ServiceOrderService;
import com.refix.main.service.WorkerService;
import com.refix.main.utils.Filter;
import com.refix.main.utils.Observer;
import com.refix.main.vaadinUI.clientsPanel.managePanels.ShowAllOrdersUI;
import com.refix.main.vaadinUI.manageOrderPanel.editChangeFinalizePanels.AreYouSureWindowUI;
import com.refix.main.vaadinUI.manageOrderPanel.editChangeFinalizePanels.ChangeWorkerPanelUI;
import com.refix.main.vaadinUI.manageOrderPanel.editChangeFinalizePanels.EditDescriptionPanelUI;
import com.refix.main.vaadinUI.manageOrderPanel.editChangeFinalizePanels.FinalizeOrderPanelUI;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ManageOrderPanelUI extends Window implements Observer{

    private ServiceOrderService serviceOrderServ;
    private PartService partServ;
    private WorkerService workerServ;

    private ServiceOrder serviceOrder;

    private double costOfUsedParts;

    private Grid<Part> warehousePartsGrid;
    private Grid<PartUse> actualPartsGrid;

    private TextField filterByNameField, filterByProducerField, filterByCategoryField;
    private Label searchLabel, descriptionLabel, workerLabel;
    private Button editDescriptionButton, changeWorkerButton, finalizeOrderButton;

    private List<PartUse> actualPartsList;
    private List<Part> warehousePartList, tempPartList;

    private VerticalLayout mainContainer;
    private HorizontalLayout horizontalLayout, footerHorizontal;
    private HorizontalLayout filterHorizontal;
    private HorizontalLayout bottomHorizontal;

    public ManageOrderPanelUI(ServiceOrder serviceOrder, ServiceOrderService serviceOrderServ, PartService partServ, WorkerService workerServ){
        super("Update order:");
        this.serviceOrderServ = serviceOrderServ;
        this.partServ = partServ;
        this.serviceOrder = serviceOrder;
        this.workerServ = workerServ;
        center();
        setModal(true);
        setWidth(1320, Unit.PIXELS);

        actualPartsList = new ArrayList<>();
        tempPartList = new ArrayList<>();
        warehousePartList = new ArrayList<>();
        warehousePartList = partServ.readAllPartWithPlusQuantity();
        actualPartsList = serviceOrderServ.getServiceOrderPartUses(serviceOrder);

        filterByNameField = new TextField("Filter by part name:");
        filterByProducerField = new TextField("Filter by part producer:");
        filterByCategoryField = new TextField("Filter by part category");
        filterByNameField.addValueChangeListener(valueChangeEvent -> filterWarehouseGrid());
        filterByProducerField.addValueChangeListener(valueChangeEvent -> filterWarehouseGrid());
        filterByCategoryField.addValueChangeListener(valueChangeEvent -> filterWarehouseGrid());

        searchLabel = new Label("Search: ");
        warehousePartsGrid = new Grid<>();
                warehousePartsGrid.setWidth(645, Unit.PIXELS);
                warehousePartsGrid.setCaption("Parts warehouse: ");
                warehousePartsGrid.addColumn(Part::getName).setCaption("Name").setWidth(100);
                warehousePartsGrid.addColumn(Part::getProducer).setCaption("Producer").setWidth(130);
                warehousePartsGrid.addColumn(Part::getCategory).setCaption("Category").setWidth(100);
                warehousePartsGrid.addColumn(Part::getQuantity).setCaption("Quantity").setWidth(100);
                warehousePartsGrid.addColumn(Part::getCost).setCaption("Value:").setWidth(100);
                warehousePartsGrid.addColumn(part -> "Attach", new ButtonRenderer<>(this::attachPartToOrder)).setWidth(100);
                warehousePartsGrid.setItems(partServ.readAllPartWithPlusQuantity());
        actualPartsGrid = new Grid<>();
                actualPartsGrid.setWidth(645, Unit.PIXELS);
                actualPartsGrid.setCaption("Warehouse value: " + calculateCostOfUsedParts() + " PLN");
                actualPartsGrid.addColumn(partUse -> partUse.getPart().getName()).setCaption("Name").setWidth(100);
                actualPartsGrid.addColumn(partUse -> partUse.getPart().getProducer()).setCaption("Producer").setWidth(130);
                actualPartsGrid.addColumn(partUse -> partUse.getPart().getCategory()).setCaption("Category").setWidth(100);
                actualPartsGrid.addColumn(partUse -> partUse.getQuantity()).setCaption("Quantity").setWidth(100);
                actualPartsGrid.addColumn(partUse -> partUse.getPart().getCost()).setCaption("Value:").setWidth(100);
                actualPartsGrid.addColumn(partUse -> "Detach", new ButtonRenderer<>(this::detachPartFromOrder)).setWidth(100);
                actualPartsGrid.setItems(serviceOrderServ.getServiceOrderPartUses(serviceOrder));
        editDescriptionButton = new Button("Edit description of order.", this::openEditDescWindow);
                editDescriptionButton.setWidth(421, Unit.PIXELS);
        changeWorkerButton = new Button("Change worker", this::openChangeWorkerWindow);
                changeWorkerButton.setWidth(421, Unit.PIXELS);
        finalizeOrderButton = new Button("Finalize order", this::openFinalizeOrderWindow);
                finalizeOrderButton.setWidth(421, Unit.PIXELS);
                    if(serviceOrder.isFinished() == true) {
                        enableAllButtons();
                    }
                    else
                    {
                        disableAllButtons();
                    }
        descriptionLabel = new Label("Order description:    " + serviceOrder.getDescription() + ", ");
        workerLabel = new Label("Actual worker: " + " " + serviceOrder.getWorker());

        mainContainer = new VerticalLayout();
        horizontalLayout = new HorizontalLayout();
        filterHorizontal = new HorizontalLayout();
        footerHorizontal = new HorizontalLayout();
        bottomHorizontal = new HorizontalLayout();

        bottomHorizontal.addComponents(descriptionLabel, workerLabel);
        filterHorizontal.addComponents(searchLabel, filterByNameField, filterByProducerField, filterByCategoryField);
        horizontalLayout.addComponents(warehousePartsGrid, actualPartsGrid);
        footerHorizontal.addComponents(editDescriptionButton, changeWorkerButton, finalizeOrderButton);
        mainContainer.addComponents(filterHorizontal, horizontalLayout, bottomHorizontal, footerHorizontal);
        setContent(mainContainer);

        AreYouSureWindowUI.registerObserver(this);
        EditDescriptionPanelUI.registerObserver(this);
    }

    private void attachPartToOrder(ClickableRenderer.RendererClickEvent rendererClickEvent){
        Part part = (Part) rendererClickEvent.getItem();
        partServ.attachPartToOrder(part.getId(), serviceOrder);
        refresh();
    }

    private void detachPartFromOrder(ClickableRenderer.RendererClickEvent rendererClickEvent){
        PartUse partUse = (PartUse) rendererClickEvent.getItem();
        partServ.detachPartFromOrder(partUse.getId());
        refresh();
    }

    private void filterWarehouseGrid(){
        if (filterByNameField.isEmpty() && filterByCategoryField.isEmpty() && filterByProducerField.isEmpty())
            warehousePartsGrid.setItems(warehousePartList);
        else {
            String name = filterByNameField.getValue();
            String producer = filterByProducerField.getValue();
            String category = filterByCategoryField.getValue();
            tempPartList = warehousePartList.stream()
                    .filter(part -> Filter.nameFilter(part, name))
                    .filter(part -> Filter.producerFilter(part, producer))
                    .filter(part -> Filter.categoryFilter(part, category))
                    .collect(Collectors.toList());
            warehousePartsGrid.setItems(tempPartList);
        }
    }

    private String calculateCostOfUsedParts(){
        double cost = partServ.calculatePartsCostInServiceOrder(actualPartsList);
        return String.valueOf(cost);
    }

    private void refresh(){
        warehousePartList = partServ.readAllPartWithPlusQuantity();
        filterWarehouseGrid();

        actualPartsList = serviceOrderServ.getServiceOrderPartUses(serviceOrder);
        actualPartsGrid.setItems(actualPartsList);
        actualPartsGrid.setCaption("Warehouse value: " + calculateCostOfUsedParts() + " PLN");
    }

    private void openEditDescWindow(Button.ClickEvent clickEvent){
        EditDescriptionPanelUI open = new EditDescriptionPanelUI(serviceOrderServ, serviceOrder);
        UI.getCurrent().addWindow(open);
    }

    private void openChangeWorkerWindow(Button.ClickEvent clickEvent){
        ChangeWorkerPanelUI open = new ChangeWorkerPanelUI(serviceOrderServ, serviceOrder, workerServ);
        UI.getCurrent().addWindow(open);
    }

    private void openFinalizeOrderWindow(Button.ClickEvent clickEvent){
        FinalizeOrderPanelUI open = new FinalizeOrderPanelUI(serviceOrderServ,partServ,serviceOrder,actualPartsList);
        UI.getCurrent().addWindow(open);
    }

    @Override
    public void update() {
        descriptionLabel.setValue("Order description:    " + serviceOrder.getDescription());

        if(serviceOrder.isFinished() == true)
            enableAllButtons();
        else
            disableAllButtons();
        close();
    }

    private void enableAllButtons(){
        editDescriptionButton.setEnabled(false);
        changeWorkerButton.setEnabled(false);
        finalizeOrderButton.setEnabled(false);
        warehousePartsGrid.setEnabled(false);
        actualPartsGrid.setEnabled(false);
    }

    private void disableAllButtons(){
        editDescriptionButton.setEnabled(true);
        changeWorkerButton.setEnabled(true);
        finalizeOrderButton.setEnabled(true);
        warehousePartsGrid.setEnabled(true);
        actualPartsGrid.setEnabled(true);
    }
}
