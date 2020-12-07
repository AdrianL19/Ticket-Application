package com.example.application.views.Administrator;

import com.example.application.ConnectionFactory.ScheduleDAO;
import com.example.application.ConnectionFactory.UsersDAO;
import com.example.application.ConnectionFactory.VehicleDAO;
import com.example.application.Model.Schedule;
import com.example.application.Model.User;
import com.example.application.Model.Vehicle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Route(value = "modifyVehicle",layout = MainViewAdministrator.class)
@PageTitle("Admin Vehicle")
public class ModifyVehicle extends VerticalLayout {
    private final ComboBox<String> drivers = new ComboBox<String>();
    private final UsersDAO user = new UsersDAO();
    private final TextField vehicleNumber = new TextField();
    private final VehicleDAO veh = new VehicleDAO();
    private final TextField numberofSlots = new TextField();
    private final Button addVehicle = new Button("Add Vehicle");
    private final Button deleteVehicle = new Button("Delete Vehicle");
    private final Button viewVehicles = new Button("View Vehicles");
    private Grid<Vehicle> grid;
    public ModifyVehicle(){
        HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        HttpSession session = req.getSession();
        try {
            User currentUser = (User) session.getAttribute("user");
            if(!currentUser.getRole().equals("Administrator")) throw new Exception();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setSizeFull();
            H1 title = new H1("Admin Vehicles");
            add(title);
            setAlignItems(Alignment.CENTER);
            firstLine();
            secondLine();
            buttonSetup();
        }catch (Exception e){
            Notification.show("Please login as an administrator first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }

    }
    public void firstLine(){
        HorizontalLayout layout = new HorizontalLayout();
        drivers.setItems(user.getDrivers());
        drivers.setPlaceholder("Drivers");
        layout.add(drivers);
        vehicleNumber.setPlaceholder("Vehicle number");
        layout.add(vehicleNumber);
        numberofSlots.setPlaceholder("Number of slots");
        layout.add(numberofSlots);
        layout.add(addVehicle,deleteVehicle,viewVehicles);
        add(layout);
    }
    public void secondLine(){
        grid = new Grid<>();
        grid.addColumn(Vehicle::getId).setHeader("ID");
        grid.addColumn(Vehicle::getDriver).setHeader("Driver's username");
        grid.addColumn(Vehicle::getNumberOfSlots).setHeader("Number of slots");
        grid.addColumn(Vehicle::getNumber).setHeader("Number");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.getColumns().forEach(col->col.setAutoWidth(true));
        add(grid);
    }
    public  void updateGrid(){
        grid.setItems(veh.getVehicles());
    }
    public void buttonSetup(){
        addVehicle.addClickListener(e->{
            Vehicle temp = new Vehicle(0,vehicleNumber.getValue(), Integer.parseInt(numberofSlots.getValue()),drivers.getValue());
            veh.insertVehicle(temp);
            updateGrid();
        });
        deleteVehicle.addClickListener(e->{
            veh.deleteVehicle(getSelected());
            updateGrid();
        });
        viewVehicles.addClickListener(e->{
            updateGrid();

        });
    }
    public String getSelected(){
        SingleSelect<Grid<Vehicle>,Vehicle> selection = grid.asSingleSelect();
        return selection.getValue().getNumber();
    }
}
