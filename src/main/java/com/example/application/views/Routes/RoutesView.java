package com.example.application.views.Routes;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.application.views.main.MainViewUser;

@Route(value = "routes", layout = MainViewUser.class)
@PageTitle("About")
public class RoutesView extends Div {

    public RoutesView() {
        setId("about-view");
        add(new Text("Rute"));
    }

}
