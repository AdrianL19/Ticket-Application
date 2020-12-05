package com.example.application.views.Administrator;

import com.example.application.ConnectionFactory.ScheduleDAO;
import com.example.application.ConnectionFactory.UsersDAO;
import com.example.application.Model.Schedule;
import com.example.application.Model.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

@Route(value = "scheduleAdmin",layout = MainViewAdministrator.class)
    @PageTitle("Admin Schedule")
    public class ScheduleDrivers extends VerticalLayout {
        private H1 title = new H1("Admin Schedule");
        private boolean bool;
        private final ComboBox<String> viewDrivers = new ComboBox<String>();
        private final Button viewScheduleofDriver = new Button("View Schedule of the Driver selected");
        private final Button viewScheduleofAllDrivers = new Button("View Schedule of all Drivers");
        private final DateTimePicker placeholderDateTimePicker = new DateTimePicker();
        private final TimePicker timePicker = new TimePicker();
        private final Button addInterval = new Button("Add Schedule");
        private final Button deleteInterval = new Button("Delete Schedule ");
        private final UsersDAO user = new UsersDAO();
        private final ScheduleDAO schedule = new ScheduleDAO();
        private Grid<Schedule> grid;
        public ScheduleDrivers() {
            HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
            HttpSession session = req.getSession();
            try {
                User currentUser = (User) session.getAttribute("user");
                currentUser.getUsername();
                setJustifyContentMode(JustifyContentMode.CENTER);
                setAlignItems(Alignment.CENTER);
                add(title);
                selectDriver();
                modifySchedule();
                driverGrid();
                buttonConfig();
            }catch (Exception e){
                Notification.show("Please login first!",3000, Notification.Position.TOP_CENTER);
                UI.getCurrent().navigate("http://localhost:8080/");
            }

        }
        public void buttonConfig(){
            viewScheduleofAllDrivers.addClickListener(e-> updateGrid1());
            viewScheduleofDriver.addClickListener(e->updateGrid2(viewDrivers.getValue()));
            addInterval.addClickListener(e->{
                addWork();
                if(bool){
                    updateGrid2(viewDrivers.getValue());
                }else{
                    updateGrid1();
                }

            });
            deleteInterval.addClickListener(e->{
                deleteWork();
                if(bool){
                    String[] split = getSelected().split(" ");
                    updateGrid2(split[0]);
                }else{
                    updateGrid1();
                }
            });
        }
        public void addWork(){
            String[] split = placeholderDateTimePicker.getValue().toString().split( "T");
            System.out.println(split[0]+" " +split[1]);
            Schedule temp = new Schedule(viewDrivers.getValue(),split[0],split[1]+":00",timePicker.getValue().toString()+":00");
            schedule.insertSchedule(temp);
        }
        public void deleteWork(){
            schedule.deleteUser(getSelected());
        }
        public void selectDriver(){
            HorizontalLayout layout = new HorizontalLayout();
            if(user.getDrivers()!=null){
                viewDrivers.setItems(user.getDrivers());
            }
            viewDrivers.setPlaceholder("View Drivers");
            layout.add(viewDrivers,viewScheduleofDriver,viewScheduleofAllDrivers);
            add(layout);
        }
        public void modifySchedule(){
            HorizontalLayout layout = new HorizontalLayout();
            placeholderDateTimePicker.setDatePlaceholder("Date");
            placeholderDateTimePicker.setTimePlaceholder("Time Start");
            placeholderDateTimePicker.setLocale(Locale.UK);
            timePicker.setPlaceholder("Time End");
            timePicker.setLocale(Locale.UK);
            layout.add(placeholderDateTimePicker,timePicker,addInterval,deleteInterval);
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
                bool=false;
        }
        public void updateGrid2(String username){
            grid.setItems(schedule.getScheduleDriver(username));
            bool=true;
    }
        public String getSelected(){
        SingleSelect<Grid<Schedule>,Schedule> selection = grid.asSingleSelect();
        return selection.getValue().getUsername() +" "+ selection.getValue().getDate() +" "+ selection.getValue().getTimeStart() +" "+ selection.getValue().getTimeEnd();
    }
}
