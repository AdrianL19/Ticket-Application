package com.example.application.views.Administrator;
import com.example.application.ConnectionFactory.UsersDAO;
import com.example.application.Model.User;
import com.example.application.views.User.MainViewUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;




@Route(value = "welcomeAdmin",layout = MainViewAdministrator.class)
@PageTitle("Admin Welcome")
public class WelcomeAdmin extends VerticalLayout {
    private H1 title = new H1("Welcome Administrator!");
    private H4 redirectUser = new H4("If you want to add/edit/delete any information about an user press the button below.");
    private Button redirectUserButton = new Button("Administrate USERS",event -> UI.getCurrent().navigate("modifyUser"));
    public WelcomeAdmin() {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setId("about-view");
        add(title,redirectUser,redirectUserButton);

    }

}
