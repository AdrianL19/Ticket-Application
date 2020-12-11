package com.example.application.views.User;

import com.example.application.ConnectionFactory.RouteDAO;
import com.example.application.Model.Rute;
import com.example.application.Model.User;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Title;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Route(value = "routesUser", layout = MainViewUser.class)
@PageTitle("Routes")
public  class RoutesView extends VerticalLayout {
    private Button viewRoutes = new Button("View Routes");
    private Button viewRoutesfrom = new Button("View Routes from departure ");
    private Button viewRoutesto = new Button("View Routes to destination");
    private RouteDAO rute = new RouteDAO();
    private H1 title = new H1("Routes");
    private ComboBox<String> plecari = new ComboBox<>();
    private ComboBox<String> destinatie = new ComboBox<>();
    private Grid<Rute> grid;

    public RoutesView() {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        HttpSession session = req.getSession();
        try {
            User currentUser = (User) session.getAttribute("user");
            if(!currentUser.getRole().equals("User")) throw new Exception();
            add(title);
            firstLine();
            gridConfig();
            buttonConfig();
        }catch(Exception e){
            Notification.show("Please login as an user first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }
    }
    public void firstLine(){
        HorizontalLayout layout = new HorizontalLayout();
        plecari.setPlaceholder("Departure");
        destinatie.setPlaceholder("Destination");
        destinatie.setItems(rute.getDestinations());
        plecari.setItems(rute.getDepartures());
        layout.add(viewRoutes,plecari,viewRoutesfrom,destinatie,viewRoutesto);
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
        grid.addColumn(Rute::getVehicleNumber).setHeader("Vehicle's Number");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.getColumns().forEach(col->col.setAutoWidth(true));
        add(grid);
    }
    public void gridUpdate(int i,String temp){
        switch (i){
            case 0: grid.setItems(rute.getRoutes());break;
            case 1: grid.setItems(rute.getRoutesByDestination(temp));break;
            case 2: grid.setItems(rute.getRoutesByDeparture(temp));break;
        }
    }
    public void buttonConfig(){
        viewRoutes.addClickListener(e->{
            gridUpdate(0,null);
        });
        viewRoutesto.addClickListener(e->{
            gridUpdate(1,destinatie.getValue());
        });
        viewRoutesfrom.addClickListener(e->{
            gridUpdate(2,plecari.getValue());
        });
    }

}