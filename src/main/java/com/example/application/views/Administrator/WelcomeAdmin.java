package com.example.application.views.Administrator;
import com.example.application.Model.User;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Route(value = "welcomeAdmin",layout = MainViewAdministrator.class)
@PageTitle("Admin Welcome")
public class WelcomeAdmin extends VerticalLayout {
    private H1 title = new H1("Welcome");
    private H4 redirectUser = new H4("If you want to add/edit/delete any information about an user press the button below.");
    private H4 redirectSchedule = new H4( "If you want to add/edit/delete any information about a driver's schedule press the button below.");
    private H4 redirectVehicle = new H4( "If you want to add/delete a vehicle press the button below.");
    private H4 redirectRoute = new H4( "If you want to add/delete a route press the button below.");
    private Button redirectUserButton = new Button("Administrate USERS",event -> UI.getCurrent().navigate("modifyUser"));
    private Button redirectScheduleButton = new Button("Administrate Driver's Schedule",event -> UI.getCurrent().navigate("scheduleAdmin"));
    private Button redirectVehicleButton = new Button("Administrate Vehicles",event -> UI.getCurrent().navigate("modifyVehicle"));
    private Button redirectRouteButton = new Button("Administrate Routes",event -> UI.getCurrent().navigate("modifyRoute"));

    public WelcomeAdmin() {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        HttpSession session = req.getSession();
        try{
            User currentUser = (User) session.getAttribute("user");
            title.setText("Welcome Administrator, "+ currentUser.getUsername());
            setId("about-view");
            add(title,redirectUser,redirectUserButton,redirectSchedule,redirectScheduleButton,redirectVehicle,redirectVehicleButton,redirectRoute,redirectRouteButton);
        } catch (Exception e) {
            Notification.show("Please login first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }

    }

}
