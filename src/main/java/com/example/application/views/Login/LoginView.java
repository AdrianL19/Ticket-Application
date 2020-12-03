package com.example.application.views.Login;


import com.example.application.ConnectionFactory.UsersDAO;
import com.example.application.Model.User;
import com.example.application.views.Administrator.WelcomeAdmin;
import com.vaadin.flow.component.ComponentUtil;
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
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedHttpSession;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Route(value = "")
@PageTitle("Login Screen")
@CssImport("./styles/views/helloworld/hello-world-view.css")
@RouteAlias(value = "")
public class LoginView extends VerticalLayout {
    public User currentUser=null;
    private boolean loginSuccesfull=false;
    private TextField name = new TextField("Username or Email");
    private PasswordField password= new PasswordField("Password");
    private H1 titlu = new H1("Ticket Application");
    private H3 register= new H3("Don't have an account? Click the button below to create an account!");
    public HttpSession session;
    public HttpServletRequest req;

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
        User temp = new User(0,name.getValue(),password.getValue(),null,null);
        for(User i : listUsers){
            if((i.getUsername().equals(temp.getUsername()) | i.getEmail().equals(temp.getUsername()))& (i.getPassword().equals(temp.getPassword())) ) {
                this.currentUser = i;
                loginSuccesfull = true;
                req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
                session = req.getSession();
                session.setAttribute("user",this.currentUser);
                navigateRole(i.getRole());
            }
        }
        if(currentUser!=null){
            Notification.show(this.currentUser.getUsername()+ " logged in successfully!",3000, Notification.Position.TOP_CENTER);
        }
        else{
            Notification.show("Error! Invalid credentials",3000,Notification.Position.TOP_CENTER);
        }
    }
    public void navigateRole(String role){
        switch (role) {
            case "User":
                UI.getCurrent().navigate("welcomeUser");
                break;
            case "Administrator":
                UI.getCurrent().navigate("welcomeAdmin");
                break;
            case "Driver":
                UI.getCurrent().navigate("welcomeDriver");
                break;
        }
    }

}
