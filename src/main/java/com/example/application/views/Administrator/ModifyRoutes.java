package com.example.application.views.Administrator;

import com.example.application.ConnectionFactory.RouteDAO;
import com.example.application.ConnectionFactory.ScheduleDAO;
import com.example.application.ConnectionFactory.VehicleDAO;
import com.example.application.Model.Rute;
import com.example.application.Model.Schedule;
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
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Route(value = "modifyRoute" ,layout = MainViewAdministrator.class)
@PageTitle("Admin Panel - Routes")
public class ModifyRoutes extends VerticalLayout {
    private final TextField destinatie = new TextField();
    private final TextField plecare = new TextField();
    private final TextField tarif = new TextField();
    private final ComboBox<String> vehicleNumber = new ComboBox<>();
    private final VehicleDAO vehicle = new VehicleDAO();
    private final RouteDAO rute = new RouteDAO();
    private final ScheduleDAO schedule = new ScheduleDAO();
    private final Button addRoute = new Button("Add Route");
    private final Button deleteRoute = new Button("Delete Route");
    private final Button viewRoutes = new Button("View Route");
    private final DatePicker datePicker = new DatePicker();
    private final TimePicker timePicker = new TimePicker();
    private final TimePicker timePicker2 = new TimePicker();

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
            setSizeFull();
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
        timePicker.setPlaceholder("Departure time");
        timePicker2.setPlaceholder("Destination time");
        vehicleNumber.setItems(vehicle.getVehicleNumber());
        layout.add(datePicker,timePicker,timePicker2,destinatie,plecare,tarif,vehicleNumber);
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
        grid.addColumn(Rute::getOraPlecare).setHeader("Departure time");
        grid.addColumn(Rute::getOraAjungere).setHeader("Destination time");
        grid.addColumn(Rute::getTarif).setHeader("Cost");
        grid.addColumn(Rute::getNumberofSlots).setHeader("Number of slots");
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
            for(Vehicle i : vehicle.getVehicles()){
                if(i.getNumber().equals(vehicleNumber.getValue())){
                    Rute temp = new Rute(datePicker.getValue().toString(),destinatie.getValue(),plecare.getValue(),Float.parseFloat(tarif.getValue()),vehicleNumber.getValue(),i.getNumberOfSlots(),timePicker2.getValue().toString()+":00",timePicker.getValue().toString()+":00");
                   if(isValid(temp)){
                       rute.insertRoute(temp);
                       gridUpdate();
                   }else{
                       Notification.show("The route you are trying to insert is not in the driver's schedule.");
                   }
                }
            }

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
    public boolean isValid(Rute ruta){
        List<Vehicle> list = vehicle.getVehicles();
        for(Vehicle i: list){
            if(ruta.getVehicleNumber().equals(i.getNumber())){
                String username=i.getDriver();
                List<Schedule> list3= schedule.getScheduleDriver(username);
                for(Schedule j : list3){
                    if(username.equals(j.getUsername())&& ruta.getData().equals(j.getDate())){
                        String[] oraPlecare = ruta.getOraPlecare().split(":");
                        String[] oraAjungere = ruta.getOraAjungere().split(":");
                        String[] oraIncepereOrar = j.getTimeStart().split(":");
                        String[] oraTerminareOrar = j.getTimeEnd().split(":");
                        if(Integer.parseInt(oraAjungere[0])>Integer.parseInt(oraTerminareOrar[0]) || Integer.parseInt(oraAjungere[0]) < Integer.parseInt(oraIncepereOrar[0])) {
                            System.out.println("1");
                            return false;
                        }else{
                           if(Integer.parseInt(oraAjungere[1])<Integer.parseInt(oraTerminareOrar[1])){
                               System.out.println("2");
                               return false;
                           }else{
                               if(Integer.parseInt(oraPlecare[0])<Integer.parseInt(oraIncepereOrar[0]) || Integer.parseInt(oraPlecare[0]) > Integer.parseInt(oraTerminareOrar[0])){
                                   System.out.println("3");
                                   return false;
                               }else {
                                   if(Integer.parseInt(oraPlecare[1])<Integer.parseInt(oraIncepereOrar[1])){
                                       System.out.println("4");
                                       return false;
                                   }
                               }
                           }
                        }
                    }
                }
            }
        }
        return true;
    }

}