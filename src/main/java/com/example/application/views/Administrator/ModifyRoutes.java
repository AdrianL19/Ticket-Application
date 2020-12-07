package com.example.application.views.Administrator;

import com.example.application.ConnectionFactory.RouteDAO;
import com.example.application.ConnectionFactory.VehicleDAO;
import com.example.application.Model.Rute;
import com.example.application.Model.User;
import com.example.application.Model.Vehicle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
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

@Route(value = "modifyRoute" ,layout = MainViewAdministrator.class)
@PageTitle("Admin Panel - Routes")
public class ModifyRoutes extends VerticalLayout {
    private final TextField destinatie = new TextField();
    private final TextField plecare = new TextField();
    private final TextField tarif = new TextField();
    private final ComboBox<String> vehicleNumber = new ComboBox<>();
    private final VehicleDAO vehicle = new VehicleDAO();
    private final RouteDAO rute = new RouteDAO();
    private final Button addRoute = new Button("Add Route");
    private final Button deleteRoute = new Button("Delete Route");
    private final Button viewRoutes = new Button("View Route");
    private final DatePicker datePicker = new DatePicker();
    private Grid<Rute> grid;
    public ModifyRoutes(){
        HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        HttpSession session = req.getSession();
        try {
            User currentUser = (User) session.getAttribute("user");
            if(!currentUser.getRole().equals("Administrator")) throw new Exception();
            H1 title = new H1("Admin Routes");
            setJustifyContentMode(JustifyContentMode.CENTER);
            setAlignItems(Alignment.CENTER);
            add(title);
            firstLine();
            secondLine();
            gridConfig();
            buttonConfig();
        }catch (Exception e){
            Notification.show("Please login as an administrator first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }
    }
    public void firstLine(){
        HorizontalLayout layout = new HorizontalLayout();
        destinatie.setPlaceholder("Destinatie");
        plecare.setPlaceholder("Plecare");
        tarif.setPlaceholder("Tarif");
        vehicleNumber.setPlaceholder("Vehicle Drivers");
        datePicker.setPlaceholder("Data");
        vehicleNumber.setItems(vehicle.getVehicleNumber());
        layout.add(datePicker,destinatie,plecare,tarif,vehicleNumber);
        add(layout);
    }
    public void secondLine(){
        HorizontalLayout layout = new HorizontalLayout();
        layout.add(addRoute,deleteRoute,viewRoutes);
        add(layout);
    }
    public void gridConfig(){
        grid = new Grid<>();
        grid.addColumn(Rute::getData).setHeader("Date");
        grid.addColumn(Rute::getPlecare).setHeader("Departure");
        grid.addColumn(Rute::getDestinatie).setHeader("Destination");
        grid.addColumn(Rute::getTarif).setHeader("Cost");
        grid.addColumn(Rute::getVehicleNumber).setHeader("Vehicle's Number");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.getColumns().forEach(col->col.setAutoWidth(true));
        add(grid);
    }
    public void gridUpdate(){
        grid.setItems(rute.getRoutes());
    }
    public void buttonConfig(){
        addRoute.addClickListener(e->{
            System.out.println(datePicker.getValue().toString());
            Rute temp = new Rute(datePicker.getValue().toString(),destinatie.getValue(),plecare.getValue(),Float.parseFloat(tarif.getValue()),vehicleNumber.getValue());
            rute.insertRoute(temp);
            gridUpdate();
        });
        deleteRoute.addClickListener(e->{
            rute.deleteRoute(getSelected());
            gridUpdate();
        });
        viewRoutes.addClickListener(e->{
            gridUpdate();
        });
    }
    public Rute getSelected(){
        SingleSelect<Grid<Rute>,Rute> selection = grid.asSingleSelect();
        return selection.getValue();
    }
}