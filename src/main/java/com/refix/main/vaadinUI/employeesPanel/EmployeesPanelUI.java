package com.refix.main.vaadinUI.employeesPanel;


import com.refix.main.entity.Worker;
import com.refix.main.service.WorkerService;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class EmployeesPanelUI extends Window{

    private WorkerService workerService;

    private Grid<Worker> workersGrid;

    private VerticalLayout mainContainer;

    public EmployeesPanelUI(WorkerService workerService){
        super("Employees panel:");
        this.workerService = workerService;
        center();
        setModal(true);
    }
}
