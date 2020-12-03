package com.example.application.views.Administrator;

import com.example.application.ConnectionFactory.ScheduleDAO;
import com.example.application.ConnectionFactory.UsersDAO;
import com.example.application.Model.Schedule;
import com.example.application.Model.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.nio.channels.SelectableChannel;
import java.time.chrono.ChronoLocalDateTime;

@Route(value = "scheduleAdmin",layout = MainViewAdministrator.class)
    @PageTitle("Admin Schedule")
    public class ScheduleDrivers extends VerticalLayout {
        private H1 title = new H1("Admin Schedule");
        private ComboBox<String> viewDrivers = new ComboBox<String>("View Drivers");
        private Button viewScheduleofDriver = new Button("View Schedule of the Driver selected");
        private Button viewScheduleofAllDrivers = new Button("View Schedule of all Drivers");
        private UsersDAO user = new UsersDAO();
        private ScheduleDAO schedule = new ScheduleDAO();
        private Grid<Schedule> grid;
        public ScheduleDrivers() {
            setJustifyContentMode(JustifyContentMode.CENTER);
            setAlignItems(Alignment.CENTER);
            add(title);
            selectDriver();
            driverGrid();
            buttonConfig();
        }
        public void buttonConfig(){
            viewScheduleofAllDrivers.addClickListener(e-> updateGrid1());
            viewScheduleofDriver.addClickListener(e->updateGrid2(viewDrivers.getValue()));
        }

        public void selectDriver(){
            HorizontalLayout layout = new HorizontalLayout();
            if(user.getDrivers()!=null){
                viewDrivers.setItems(user.getDrivers());
            }
            layout.add(viewDrivers,viewScheduleofDriver,viewScheduleofAllDrivers);
            add(layout);
        }
        public void driverGrid(){
                grid = new Grid<>();
                grid.addColumn(Schedule::getUsername).setHeader("Username");
                grid.addColumn(Schedule::getDate).setHeader("Date");
                grid.addColumn(Schedule::getTimeStart).setHeader("Time Start");
                grid.addColumn(Schedule::getTimeEnd).setHeader("Time End");
                grid.setSelectionMode(Grid.SelectionMode.SINGLE);
                grid.getColumns().forEach(col->col.setAutoWidth(true));
                add(grid);
        }
        public void updateGrid1(){
                grid.setItems(schedule.getUsers());
        }
        public void updateGrid2(String username){
            grid.setItems(schedule.getScheduleDriver(username));
    }
    }
