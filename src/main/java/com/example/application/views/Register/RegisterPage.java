package com.example.application.views.Register;

import com.example.application.ConnectionFactory.UsersDAO;
import com.example.application.Model.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.List;

@Route(value = "register")
@PageTitle("Register Page")
@CssImport("./styles/views/helloworld/hello-world-view.css")

public class RegisterPage extends VerticalLayout {

    private TextField name = new TextField("Username");
    private PasswordField password= new PasswordField("Password");
    private PasswordField retypePassword = new PasswordField("Retype Password");


    public RegisterPage() {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        Button button = new Button("Register user", event -> registerUser()) ;
        add(name,password,retypePassword,button);
    }

    private void registerUser() {
        UsersDAO users = new UsersDAO();

        if(password.getValue().equals(retypePassword.getValue())){
            User temp = new User(name.getValue(),password.getValue(),"User");
            users.insertUser(temp);
            UI.getCurrent().navigate("");
            Notification.show("Registration complete!");
        }else{
            Notification.show("Passwords do not match!");
        }

    }

}

