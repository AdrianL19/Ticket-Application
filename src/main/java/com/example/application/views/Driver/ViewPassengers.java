package com.example.application.views.Driver;

import com.example.application.ConnectionFactory.RouteDAO;
import com.example.application.ConnectionFactory.VehicleDAO;
import com.example.application.Model.Rute;
import com.example.application.Model.User;
import com.example.application.Model.Vehicle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Route(value = "passDriver",layout = MainViewDriver.class)
@PageTitle("Driver Passengers")
public class ViewPassengers extends VerticalLayout {
    private H1 title = new H1("View Passengers");
    private VehicleDAO vehicle = new VehicleDAO();
    private RouteDAO rute = new RouteDAO();
    private User currentUser;
    private Button searchRoutes = new Button("View my routes");
    private Button nextPage = new Button("Continue",new Icon(VaadinIcon.EXIT));
    private Grid<Rute> grid;
    public ViewPassengers(){
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        HttpSession session = req.getSession();
        try{
            currentUser = (User) session.getAttribute("user");
            if(!currentUser.getRole().equals("Driver")) throw new Exception();
            add(title);
            configVisualization();
            buttonConfig();
        }catch(Exception e){
            Notification.show("Please login as an driver first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }
    }
    public void configVisualization(){
        add(searchRoutes);
        setSizeFull();
        gridConfig();
    }
    public void gridConfig(){
        grid = new Grid<>();
        remove(grid);
        grid.addColumn(Rute::getData).setHeader("Date");
        grid.addColumn(Rute::getPlecare).setHeader("Departure");
        grid.addColumn(Rute::getDestinatie).setHeader("Destination");
        grid.addColumn(Rute::getTarif).setHeader("Cost");
        grid.addColumn(Rute::getVehicleNumber).setHeader("Vehicle's Number");
        grid.addColumn(Rute::getNumberofSlots).setHeader("Slots available");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.getColumns().forEach(col->col.setAutoWidth(true));
        add(grid);
    }
    public void buttonConfig(){
        searchRoutes.addClickListener(e->{
            for(Vehicle i : vehicle.getVehicles()){
                if(i.getDriver().equals(currentUser.getUsername())){
                    grid.setItems(rute.getSearchbyDriver(i.getNumber()));
                }
            }
            nextPage.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            add(nextPage);

        });
        nextPage.addClickListener(e->{
            if(getSelected()==null){
                Notification.show("Please select a route from the list below", 3000, Notification.Position.TOP_CENTER);
            }else{
                UI.getCurrent().navigate("passNumber");
            }
        });
    }
    public Rute getSelected(){
        SingleSelect<Grid<Rute>,Rute> selection = grid.asSingleSelect();
        return selection.getValue();
    }
}
