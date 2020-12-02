package com.example.application.views.Welcome;

import com.vaadin.flow.component.html.Div;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.application.views.main.MainViewUser;


@Route(value = "welcomeAdmin", layout = MainViewUser.class)
@PageTitle("Admin Welcome")
public class WelcomeAdmin extends VerticalLayout {
    private H1 title = new H1("Welcome Administrator!");
    private H4 redirectUser = new H4("If you want to add/edit/delete any information about an user press the button below.");
    public WelcomeAdmin() {
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        setId("about-view");

        setSpacing(false);
        add(title,redirectUser);
    }

}
