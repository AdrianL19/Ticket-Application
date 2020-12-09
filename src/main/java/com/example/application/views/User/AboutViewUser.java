package com.example.application.views.User;

import com.example.application.Model.User;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.application.views.User.MainViewUser;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Route(value = "about", layout = MainViewUser.class)
@PageTitle("About")
public class AboutViewUser extends VerticalLayout {

    public AboutViewUser() {
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        HttpSession session = req.getSession();
        try{
            User currentUser = (User) session.getAttribute("user");
            if(!currentUser.getRole().equals("User")) throw new Exception();
            add(new H2("This project was made by Adrian Borca"));
            add(new H2("Software engineer - 2020-2021 Laboratory"));
            add(new H2("Group 30235 - Technical University of Cluj Napoca"));
            add(new H2("Contact"));
            add(new H2("Email: adrianborca99@yahoo.com"));
            add(new H1(""));
            add(new H1(""));
            add(new H1(""));
            add(new H2(""));
            add(new H6("Powered by Smurfrider Corki - Copyright © 2020"));
            add(new H6("All rights reserved."));
        }catch(Exception e){
            Notification.show("Please login as an user first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }
    }

}
