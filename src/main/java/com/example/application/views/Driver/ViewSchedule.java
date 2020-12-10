package com.example.application.views.Driver;

import com.example.application.ConnectionFactory.ScheduleDAO;
import com.example.application.ConnectionFactory.UsersDAO;
import com.example.application.Model.Schedule;
import com.example.application.Model.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
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

@Route(value = "scheduleDriver",layout = MainViewDriver.class)
@PageTitle("Admin Schedule")
public class ViewSchedule extends VerticalLayout {
    private H1 title = new H1("Driver Schedule");
    private final Button viewScheduleofDriver = new Button("View Your Schedule ");
    private final Button viewScheduleofDate = new Button("View Your Schedule of a Date Selected");
    private final DatePicker placeholderDateTimePicker = new DatePicker();
    private final UsersDAO user = new UsersDAO();
    private User currentUser;
    private final ScheduleDAO schedule = new ScheduleDAO();
    private Grid<Schedule> grid;
    public ViewSchedule() {
        HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        HttpSession session = req.getSession();
        try {
            currentUser = (User) session.getAttribute("user");
            if(!currentUser.getRole().equals("Driver")) throw new Exception();
            setSizeFull();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setAlignItems(Alignment.CENTER);
            add(title);
            HorizontalLayout layout = new HorizontalLayout();
            placeholderDateTimePicker.setPlaceholder("Pick a date");
            layout.add(viewScheduleofDriver,viewScheduleofDate,placeholderDateTimePicker);
            add(layout);
            driverGrid();
            buttonConfig();
        }catch (Exception e){
            Notification.show("Please login as an Driver first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }

    }
    public void updateGrid1(String up){
        System.out.println(up);
        grid.setItems(schedule.getScheduleDate(up));
    }
    public void buttonConfig(){
        viewScheduleofDriver.addClickListener(e->updateGrid2(currentUser.getUsername()));
        viewScheduleofDate.addClickListener(e-> updateGrid1(placeholderDateTimePicker.getValue().toString()));

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
    public void updateGrid2(String username){
        grid.setItems(schedule.getScheduleDriver(username));
    }

}
