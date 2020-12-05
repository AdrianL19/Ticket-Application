package com.example.application.views.User;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route(value = "welcomeUser", layout = MainViewUser.class)
@PageTitle("Welcome User")

public class WelcomeUser extends Div {

    public WelcomeUser() {
        setId("about-view");
        add(new Text("Welcome User!"));
    }

}
