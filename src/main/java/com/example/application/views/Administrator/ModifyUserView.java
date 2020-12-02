package com.example.application.views.Administrator;

import com.example.application.ConnectionFactory.UsersDAO;
import com.example.application.Model.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "modifyUser" ,layout = MainViewAdministrator.class)
@PageTitle("Admin Panel - Users")
public class ModifyUserView extends HorizontalLayout {
    private TextField username = new TextField("Username");
    private TextField password = new TextField("Password");
    private UsersDAO user = new UsersDAO();
    private ComboBox<String> roles = new ComboBox<>();
    private Button addUser = new Button("Add User");
    private Button editUser = new Button("Edit User");
    private Button deleteUser = new Button("Delete User");
    private Button viewUsers = new Button("View Users");
    public ModifyUserView(){
        getStyle().set("border", "1px solid #9E9E9E");
        setPadding(false);
        setMargin(true);
        setSpacing(true);
        userInfosBox();
        buttonsBox();
        buttonListeners();
    }
    public void userInfosBox(){
        VerticalLayout firstBox = new VerticalLayout();
        firstBox.add(username,password);
        comboBoxInit();
        firstBox.add(roles);
        add(firstBox);
    }
    public void buttonsBox(){
        VerticalLayout secondBox = new VerticalLayout();
        secondBox.add(addUser);
        secondBox.add(editUser);
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
        addUser.addClickListener(e-> user.insertUser(new User(username.getValue(),password.getValue(),roles.getValue())));
        deleteUser.addClickListener(e->user.deleteUser(username.getValue()));
    }
}
