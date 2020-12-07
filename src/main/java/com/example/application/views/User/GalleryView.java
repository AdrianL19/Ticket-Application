package com.example.application.views.User;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "vehicleUser", layout = MainViewUser.class)
@PageTitle("Gallery/Vehicles")
public class GalleryView extends VerticalLayout {

    public GalleryView() {
        setId("about-view");

    }


}
