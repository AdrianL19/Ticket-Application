package com.example.application.views.about;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.application.views.main.MainViewUser;

@Route(value = "about", layout = MainViewUser.class)
@PageTitle("About")
public class AboutViewUser extends Div {

    public AboutViewUser() {
        setId("about-view");
        add(new Text("Content placeholder"));
    }

}
