package com.refix.main.vaadinUI.manageOrderPanel.editChangeFinalizePanels;


import com.refix.main.entity.ServiceOrder;
import com.refix.main.entity.Worker;
import com.refix.main.service.ServiceOrderService;
import com.refix.main.service.WorkerService;
import com.vaadin.ui.*;

import java.util.stream.Collectors;


public class ChangeWorkerPanelUI extends Window{

    private ServiceOrderService serviceOrderServ;
    private WorkerService workerServ;

    private ServiceOrder serviceOrder;

    private Label actualWorkerLabel;
    private ListSelect<Worker> workerListSelect;
    private Button cancelButton, confirmButton;

    private VerticalLayout mainContiner;
    private HorizontalLayout buttonsHorizonalLayout;


    public ChangeWorkerPanelUI(ServiceOrderService serviceOrderServ, ServiceOrder serviceOrder, WorkerService workerServ){
        super("Change worker");
        this.serviceOrderServ = serviceOrderServ;
        this.serviceOrder = serviceOrder;
        this.workerServ = workerServ;
        center();
        setModal(true);

        actualWorkerLabel = new Label("Actual employee: " + serviceOrder.getWorker().toString());
        workerListSelect = new ListSelect<>();
                workerListSelect.setWidth(415, Unit.PIXELS);
                workerListSelect.setItems(workerServ.readEmployedWorkers());
        cancelButton = new Button("Cancel", clickEvent -> close());
                cancelButton.setWidth(201,Unit.PIXELS);
        confirmButton = new Button("Confirm change", clickEvent -> changeWorker());
                confirmButton.setWidth(201,Unit.PIXELS);

        buttonsHorizonalLayout = new HorizontalLayout();
        mainContiner = new VerticalLayout();

        buttonsHorizonalLayout.addComponents(cancelButton, confirmButton);
        mainContiner.addComponents(actualWorkerLabel, workerListSelect, buttonsHorizonalLayout);

        setContent(mainContiner);
    }

    private void changeWorker(){
        Worker worker = workerListSelect.getSelectedItems().stream().collect(Collectors.toList()).get(0);
        serviceOrderServ.updateServiceOrder(serviceOrder, worker);
        close();
    }
}
