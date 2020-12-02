package com.example.application.views.Welcome;

import com.vaadin.flow.component.html.Div;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.application.views.main.MainViewUser;

@Route(value = "welcomeAdmin", layout = MainViewUser.class)
@PageTitle("Admin Welcome")
public class WelcomeAdmin extends VerticalLayout {

    public WelcomeAdmin() {
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        setId("about-view");

        add(new Text("Welcome Administrator!"));
    }

}
