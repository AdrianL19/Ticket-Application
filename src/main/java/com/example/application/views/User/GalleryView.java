package com.example.application.views.User;

import com.example.application.ConnectionFactory.UsersDAO;
import com.example.application.ConnectionFactory.VehicleDAO;
import com.example.application.Model.User;
import com.example.application.Model.Vehicle;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
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

@Route(value = "vehicleUser", layout = MainViewUser.class)
@PageTitle("Gallery/Vehicles")
public class GalleryView extends VerticalLayout {

    private final VehicleDAO veh = new VehicleDAO();
    private final Button viewVehicles = new Button("View Vehicles");
    private Grid<Vehicle> grid;
    public GalleryView(){
        HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        HttpSession session = req.getSession();
        try {
            User currentUser = (User) session.getAttribute("user");
            if(!currentUser.getRole().equals("User")) throw new Exception();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setSizeFull();
            H1 title = new H1("Vehicles information");
            add(title);
            setAlignItems(Alignment.CENTER);
            add(viewVehicles);
            secondLine();
            buttonSetup();
        }catch (Exception e){
            Notification.show("Please login as an user first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }

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
        viewVehicles.addClickListener(e->{
            updateGrid();

        });
    }

}
