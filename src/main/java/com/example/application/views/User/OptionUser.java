package com.example.application.views.User;

import com.example.application.ConnectionFactory.RouteDAO;
import com.example.application.ConnectionFactory.VehicleDAO;
import com.example.application.Model.Rute;
import com.example.application.Model.User;
import com.example.application.Model.Vehicle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.charts.model.Title;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Route(value = "optionUser", layout = MainViewUser.class)
@PageTitle("Select Page")

public class OptionUser extends VerticalLayout {
    private ProgressBar progressBar = new ProgressBar();
    private H1 title = new H1("Option User");
    private H4 remainingSlots = new H4();
    private VehicleDAO vehicle = new VehicleDAO();
    private HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
    private HttpSession session = req.getSession();
    public OptionUser(){
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        try{
            User currentUser = (User) session.getAttribute("user");
            if(!currentUser.getRole().equals("User")) throw new Exception();
            configVisualization();
            buttonConfig();
        }catch(Exception e){
            Notification.show("Please login as an user first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }
    }
    public void configVisualization(){
        progressBar.setValue(0.33);
        add(title,progressBar);


    }
    public void buttonConfig(){

    }
    private int remaingSlots(){
        for(Vehicle i : vehicle.getVehicles()){
            Rute currentRoute = (Rute) session.getAttribute("selectedRoute");
            //if(currentRoute.getVehicleNumber().equals(i.get))
        }
        return 0;
    }
}
