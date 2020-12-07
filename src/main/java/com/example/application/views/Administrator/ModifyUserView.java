package com.example.application.views.Administrator;

import com.example.application.ConnectionFactory.UsersDAO;
import com.example.application.Model.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Route(value = "modifyUser" ,layout = MainViewAdministrator.class)
@PageTitle("Admin Panel - Users")
public class ModifyUserView extends VerticalLayout {
    private TextField username = new TextField("Username");
    private TextField password = new TextField("Password");
    private TextField email = new TextField("Email");
    private UsersDAO user = new UsersDAO();
    private ComboBox<String> roles = new ComboBox<>();
    private Button addUser = new Button("Add User");
    private Button editUsername = new Button("Change User");
    private Button editPassword = new Button("Change Password");
    private Button editEmail = new Button("Change Email");
    private Button editRole = new Button("Change Role");
    private Button deleteUser = new Button("Delete User");
    private Button viewUsers = new Button("View Users");
    private Grid<User> grid;

    public ModifyUserView(){
        HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        HttpSession session = req.getSession();
        try {
            User currentUser = (User) session.getAttribute("user");
            if(!currentUser.getRole().equals("Administrator")) throw new Exception();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setAlignItems(Alignment.CENTER);
            setSizeFull();
            setPadding(false);
            setMargin(true);
            setSpacing(true);
            userInfosBox();
            buttonsBox();
            buttonListeners();
            gridSetup();
        }catch (Exception e){
            Notification.show("Please login as an administrator first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }

    }
    public void gridSetup(){
        grid = new Grid<>();
        grid.addColumn(User::getId).setHeader("ID");
        grid.addColumn(User::getUsername).setHeader("Username");
        grid.addColumn(User::getPassword).setHeader("Password");
        grid.addColumn(User::getEmail).setHeader("Email");
        grid.addColumn(User::getRole).setHeader("Role");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.getColumns().forEach(col->col.setAutoWidth(true));
        add(grid);
    }
    public void gridUpdate(){
        grid.setItems(user.getUsers());
    }
    public void userInfosBox(){
        HorizontalLayout firstBox = new HorizontalLayout();
        firstBox.add(username,password,email);
        comboBoxInit();
        firstBox.add(roles);
        add(firstBox);

    }
    public void buttonsBox(){
        HorizontalLayout secondBox = new HorizontalLayout();
        secondBox.add(addUser);
        secondBox.add(editUsername);
        secondBox.add(editPassword);
        secondBox.add(editEmail);
        secondBox.add(editRole);
        secondBox.add(deleteUser);
        secondBox.add(viewUsers);
        add(secondBox);
    }
    public void comboBoxInit(){
        roles.setItems("Administrator","User","Driver");
        roles.setValue("Administrator");
        roles.setLabel("Roles");
    }
    public void buttonListeners(){
        addUser.addClickListener(e-> {
            user.insertUser(new User(0,username.getValue(),password.getValue(),email.getValue(),roles.getValue()));
            gridUpdate();
        });
        deleteUser.addClickListener(e->{
            user.deleteUser(getSelected());
            gridUpdate();
        });
        viewUsers.addClickListener(e-> gridUpdate());
        editUsername.addClickListener(e->{
            user.updateUsername(username.getValue(),getSelected());
            gridUpdate();
        });
        editEmail.addClickListener(e->{
            user.updateEmail(email.getValue(),getSelected());
            gridUpdate();
        });
        editPassword.addClickListener(e->{
            user.updatePassword(password.getValue(),getSelected());
            gridUpdate();
        });
        editRole.addClickListener(e->{
            user.updateRole(roles.getValue(),getSelected());
            gridUpdate();
        });

    }
    public String getSelected(){
        SingleSelect<Grid<User>,User> selection = grid.asSingleSelect();
        System.out.println(selection.getValue().getUsername());
        return selection.getValue().getUsername();
    }
}
