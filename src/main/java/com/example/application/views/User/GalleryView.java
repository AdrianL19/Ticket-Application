package com.example.application.views.User;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "gallery", layout = MainViewUser.class)
@PageTitle("Gallery")
public class GalleryView extends Div {

    public GalleryView() {
        setId("about-view");
        add(new Text("Gallery"));
    }


}
