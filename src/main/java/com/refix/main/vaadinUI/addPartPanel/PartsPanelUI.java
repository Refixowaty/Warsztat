package com.refix.main.vaadinUI.addPartPanel;


import com.refix.main.entity.Part;
import com.refix.main.service.PartService;
import com.refix.main.utils.Filter;
import com.refix.main.utils.Observer;
import com.refix.main.utils.WorkshopUtils;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartsPanelUI extends Window implements Observer{

    private PartService partServ;

    private Grid<Part> partGrid;
    private List<Part> filteredParts;
    private Button addPart, updateQuantity, closeButton;
    private TextField filterByNameField, filterByProducerField, filterByCategoryField;
    private Label searchLabel;

    private VerticalLayout vericalPart, mainContainer;
    private HorizontalLayout horizontalSearch, horizontalButtons;

    public PartsPanelUI(PartService partServ){
        super("Parts Panel:");
        this.partServ = partServ;
        filteredParts = new ArrayList<>();
        center();
        setModal(true);
        setWidth(1200, Unit.PIXELS);

        partGrid = new Grid<>();
                partGrid.setWidth(1100, Unit.PIXELS);
                partGrid.setHeight(300, Unit.PIXELS);
                partGrid.setCaption("Kind of part: ");
                partGrid.addColumn(Part::getId).setCaption("Part ID:");
                partGrid.addColumn(Part::getName).setCaption("Name: ");
                partGrid.addColumn(Part::getProducer).setCaption("Producer: ");
                partGrid.addColumn(part -> WorkshopUtils.convertPartBooleanToValue(part.isNewPart()))
                        .setCaption("New/Used");
                partGrid.addColumn(Part::getCost).setCaption("Value(PLN)");
                partGrid.addColumn(Part::getCategory).setCaption("Category: ");
                partGrid.addColumn(Part::getQuantity).setCaption("Quantity: ");
                partGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
                partGrid.setItems(partServ.readAllParts());
        addPart = new Button("Add new part", this::addPart);
                addPart.setWidth(380,Unit.PIXELS);
        updateQuantity = new Button("Update Quantity", this::updateQuantity);
                updateQuantity.setWidth(380,Unit.PIXELS);
        closeButton = new Button("End");
                closeButton.setWidth(380, Unit.PIXELS);
        searchLabel = new Label("Search: ");
        filterByNameField = new TextField("Filter by part name:");
        filterByProducerField = new TextField("Filter by part producer:");
        filterByCategoryField = new TextField("Filter by part category");

        horizontalSearch = new HorizontalLayout();
        vericalPart = new VerticalLayout();
        mainContainer = new VerticalLayout();
        horizontalButtons = new HorizontalLayout();

        configureFiltering();

        horizontalSearch.addComponents(searchLabel, filterByNameField, filterByProducerField, filterByCategoryField);
        vericalPart.addComponents(partGrid);
        horizontalButtons.addComponents(addPart,updateQuantity,closeButton);
        mainContainer.addComponents(horizontalSearch, vericalPart, horizontalButtons);

        setContent(mainContainer);

        AddPartUI.registerObserver(this);
        UpdatePartQuantityUI.registerObserver(this);
    }

    private void configureFiltering(){
        filterByNameField.addValueChangeListener(text -> filterWarehouseGrid());
        filterByCategoryField.addValueChangeListener(text -> filterWarehouseGrid());
        filterByProducerField.addValueChangeListener(text -> filterWarehouseGrid());
    }

    private void filterWarehouseGrid(){
        List<Part> parts = partServ.readAllParts();
        if (filterByNameField.isEmpty() && filterByCategoryField.isEmpty() && filterByProducerField.isEmpty())
            partGrid.setItems(parts);
        else {
            String name = filterByNameField.getValue();
            String producer = filterByProducerField.getValue();
            String category = filterByCategoryField.getValue();
            filteredParts = parts.stream()
                    .filter(part -> Filter.nameFilter(part, name))
                    .filter(part -> Filter.producerFilter(part, producer))
                    .filter(part -> Filter.categoryFilter(part, category))
                    .collect(Collectors.toList());
            partGrid.setItems(filteredParts);
        }
    }

    public void addPart(Button.ClickEvent clickEvent){
        AddPartUI addPartUI = new AddPartUI(partServ);
        UI.getCurrent().addWindow(addPartUI);
    }

    public void updateQuantity(Button.ClickEvent clickEvent){
        Part part = partGrid.getSelectedItems().stream().collect(Collectors.toList()).get(0);
        long id = part.getId();
        UpdatePartQuantityUI updatePartQuantityUI = new UpdatePartQuantityUI(id, partServ);
        UI.getCurrent().addWindow(updatePartQuantityUI);
    }

    @Override
    public void update() {
        partGrid.setItems(partServ.readAllParts());
    }
}
