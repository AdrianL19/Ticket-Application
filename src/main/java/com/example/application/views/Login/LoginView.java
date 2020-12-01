package com.example.application.views.Login;


import com.example.application.ConnectionFactory.UsersDAO;
import com.example.application.Model.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.List;

@Route(value = "")
@PageTitle("Login Screen")
@CssImport("./styles/views/helloworld/hello-world-view.css")
@RouteAlias(value = "")
public class LoginView extends VerticalLayout {

    private TextField name = new TextField("Username");
    private PasswordField password= new PasswordField("Password");
    private H1 titlu = new H1("Ticket Application");
    private H3 register= new H3("Don't have an account? Click the button below to create an account!");

    public LoginView() {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        Button button = new Button("Login", event -> checkLogin()) ;
        Button button2 = new Button("Register Now", event -> UI.getCurrent().navigate("register")) ;
        add(titlu,name,password,button,register,button2);
    }

    public void checkLogin() {
        UsersDAO users = new UsersDAO();
        List<User> listUsers = users.getUsers();
        User temp = new User(name.getValue(),password.getValue(),null);
        for(User i : listUsers){
            if((i.getUsername().equals(temp.getUsername()))& (i.getPassword().equals(temp.getPassword())) ){
                switch (i.getRole()){
                    case "User":
                        UI.getCurrent().navigate("welcomeUser");
                        break;
                    case "Administrator":
                        UI.getCurrent().navigate("");
                        break;
                    case "Sofer":
                        UI.getCurrent().navigate("");
                        break;
                }
            }else{
                Notification.show("Error! Invalid Username or password!");
            }
        }


    }

}
