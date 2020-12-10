package com.example.application.views.Driver;

import com.example.application.ConnectionFactory.BileteDAO;
import com.example.application.Model.Bilet;
import com.example.application.Model.Rute;
import com.example.application.Model.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Route(value = "passNumber",layout = MainViewDriver.class)
@PageTitle("Driver Passengers")
public class ViewPassengerNumber extends VerticalLayout {
    private Button viewHistory = new Button("View Passengers");
    private Grid<Bilet> grid;
    private BileteDAO bilete = new BileteDAO();
    private User currentUser;
    private Rute currentRoute;
    public ViewPassengerNumber(){
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        HttpSession session = req.getSession();
        try{
            currentUser = (User) session.getAttribute("user");
            currentRoute = (Rute) session.getAttribute("ruta");
            if(!currentUser.getRole().equals("Driver")) throw new Exception();
            add(new H1("Passengers"));
            add(viewHistory);
            gridConfig();
            buttonConfig();
        }catch(Exception e){
            Notification.show("Please login as an user first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }
    }
    private void buttonConfig() {

        viewHistory.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        viewHistory.addClickListener(e->grid.setItems(bilete.viewBiletebyNumber(currentUser.getUsername(), currentRoute.getVehicleNumber(),currentRoute.getData())));
    }
    public void gridConfig(){
        grid = new Grid<>();
        remove(grid);
        grid.addColumn(Bilet::getId).setHeader("Ticket ID");
        grid.addColumn(Bilet::getBuyer).setHeader("Buyer username");
        grid.addColumn(Bilet::getData).setHeader("Date of route");
        grid.addColumn(Bilet::getDestinatie).setHeader("Destination");
        grid.addColumn(Bilet::getPlecare).setHeader("Departure");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.getColumns().forEach(col->col.setAutoWidth(true));
        setSizeFull();
        add(grid);
    }
}
