package com.example.application.views.User;

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

@Route(value = "welcomeUser", layout = MainViewUser.class)
@PageTitle("Welcome User")

public class WelcomeUser extends VerticalLayout {
        private H1 title = new H1();
        private H4 buyTicket = new H4("If you want to buy a ticket or to book a slot for a route  press the button below.");
        private Button buyTicketButton = new Button("Buy Ticket / Book Slot",event -> UI.getCurrent().navigate("buyZone"));
        private H4 redirectRoutes = new H4("If you want to view any information about the available routes press the button below.");
        private H4 redirectVehicle = new H4( "If you want to view a vehicle vehicle's information press the button below.");
        private H4 redirectAbout = new H4( "If you want to contact us press the button bellow.");
        private Button redirectRouteButton = new Button("View Routes", event -> UI.getCurrent().navigate("routesUser"));
        private Button redirectVehicleButton = new Button("View Vehicles", event -> UI.getCurrent().navigate("vehicleUser"));
        private Button redirectAboutButton = new Button( "About/Contact",event -> UI.getCurrent().navigate("about"));


    public WelcomeUser() {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        HttpSession session = req.getSession();
        try{
            User currentUser = (User) session.getAttribute("user");
            if(!currentUser.getRole().equals("User")) throw new Exception();
            title.setText("Welcome User, "+ currentUser.getUsername());
            setId("about-view");
            add(title,buyTicket,buyTicketButton,redirectRoutes,redirectRouteButton,redirectVehicle,redirectVehicleButton,redirectAbout,redirectAboutButton);
        }catch(Exception e){
            Notification.show("Please login as an user first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }
    }

}
