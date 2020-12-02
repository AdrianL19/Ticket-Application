package com.example.application.views.User;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "routes", layout = MainViewUser.class)
@PageTitle("Routes")
public  class RoutesView extends Div {

    public RoutesView() {
        setId("about-view");
        add(new Text("Rute"));
    }

}