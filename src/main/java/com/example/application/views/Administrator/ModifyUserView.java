package com.example.application.views.Administrator;

import com.example.application.ConnectionFactory.UsersDAO;
import com.example.application.Model.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
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
    private UsersDAO user = new UsersDAO();
    private ComboBox<String> roles = new ComboBox<>();
    private Button addUser = new Button("Add User");
    private Button editUsername = new Button("Change User");
    private Button editPassword = new Button("Change Password");
    private Button editRole = new Button("Change Role");
    private Button deleteUser = new Button("Delete User");
    private Button viewUsers = new Button("View Users");
    private Grid<User> grid;
    public HttpSession session;
    public HttpServletRequest req;
    public ModifyUserView(){
        getStyle().set("border", "1px solid #9E9E9E");
        setPadding(false);
        setMargin(true);
        setSpacing(true);
        userInfosBox();
        buttonsBox();
        buttonListeners();
        gridSetup();
    }
    public void gridSetup(){
        grid = new Grid<>();
        grid.addColumn(User::getUsername).setHeader("Username");
        grid.addColumn(User::getPassword).setHeader("Password");
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
        firstBox.add(username,password);
        comboBoxInit();
        firstBox.add(roles);
        add(firstBox);

    }
    public void buttonsBox(){
        HorizontalLayout secondBox = new HorizontalLayout();
        secondBox.add(addUser);
        secondBox.add(editUsername);
        secondBox.add(editPassword);
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
            user.insertUser(new User(username.getValue(),password.getValue(),roles.getValue()));
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
