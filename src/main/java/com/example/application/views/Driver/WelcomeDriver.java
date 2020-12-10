package com.example.application.views.Administrator;
import com.example.application.Model.User;

import com.example.application.views.Driver.MainViewDriver;
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


@Route(value = "welcomeDriver",layout = MainViewDriver.class)
@PageTitle("Driver Welcome")
public class WelcomeDriver extends VerticalLayout {
    private H1 title = new H1("");
    private H4 redirectSchedule = new H4( "If you want to view your schedule press the button below.");
    private Button redirectScheduleButton = new Button( "View Schedule", event -> UI.getCurrent().navigate("scheduleDriver"));
    private H4 redirectPassengers = new H4( "If you want to view your passengers information press the button below.");
    private Button redirectPassengersButton = new Button( "View Passengers", event -> UI.getCurrent().navigate("passDriver"));
    public HttpSession session;
    public HttpServletRequest req;
    public WelcomeDriver() {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        HttpSession session = req.getSession();
        try{
            User currentUser = (User) session.getAttribute("user");
            if(!currentUser.getRole().equals("Driver")) throw new Exception();
            title.setText("Welcome Driver, "+ currentUser.getUsername());
            setId("about-view");
            add(title,redirectSchedule,redirectScheduleButton,redirectPassengers,redirectPassengersButton);
        } catch (Exception e) {
            Notification.show("Please login as an driver first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }


    }

}
