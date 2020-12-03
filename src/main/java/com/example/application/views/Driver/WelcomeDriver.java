package com.example.application.views.Administrator;
import com.example.application.Model.User;

import com.example.application.views.Driver.MainViewDriver;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;

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
    private H1 title = new H1("Welcome");
    public HttpSession session;
    public HttpServletRequest req;
    public WelcomeDriver() {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        session = req.getSession();
        User currentUser = (User) session.getAttribute("user");
        title.setText("Welcome Driver, "+ currentUser.getUsername());
        setId("about-view");
        add(title);

    }

}
