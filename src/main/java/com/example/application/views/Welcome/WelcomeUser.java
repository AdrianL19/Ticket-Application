package com.example.application.views.Welcome;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.application.views.main.MainViewUser;

@Route(value = "welcomeUser", layout = MainViewUser.class)
@PageTitle("About")
public class WelcomeUser extends Div {

    public WelcomeUser() {
        setId("about-view");
        add(new Text("Welcome User!"));
    }

}
