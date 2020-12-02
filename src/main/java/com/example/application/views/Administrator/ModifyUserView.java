package com.example.application.views.Administrator;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "modifyUser" ,layout = MainViewAdministrator.class)
@PageTitle("Admin Panel - Users")
public class ModifyUserView extends VerticalLayout {
    public ModifyUserView(){

    }
}
