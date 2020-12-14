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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Route(value = "register")
@PageTitle("Register Page")
@CssImport("./styles/views/helloworld/hello-world-view.css")

public class RegisterPage extends VerticalLayout {

    private TextField name = new TextField("Username");
    private TextField email = new TextField("Email");
    private PasswordField password= new PasswordField("Password");
    private PasswordField retypePassword = new PasswordField("Retype Password");
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public RegisterPage() {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        Button button = new Button("Register user", event -> registerUser()) ;
        add(name,email,password,retypePassword,button);
    }

    private void registerUser() {
        UsersDAO users = new UsersDAO();

        if(validate(email.getValue())){
            if(password.getValue().equals(retypePassword.getValue())){
                User temp = new User(0,name.getValue(),password.getValue(),email.getValue(),"User");
                users.insertUser(temp);
                UI.getCurrent().navigate("");
                Notification.show("Registration complete!");
            }else{
                Notification.show("Passwords do not match!");
            }
        }else{
            Notification.show("Please enter a valid email address!");
        }


    }

}

