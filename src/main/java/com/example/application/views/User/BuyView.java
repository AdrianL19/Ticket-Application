package com.example.application.views.User;


import com.example.application.ConnectionFactory.RouteDAO;
import com.example.application.Model.Rute;
import com.example.application.Model.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Route(value = "buyZone", layout = MainViewUser.class)
@PageTitle("Buy Ticket")
public class BuyView extends VerticalLayout {
    private H1 title = new H1("Buy/Book Menu");
    private ProgressBar progressBar = new ProgressBar();
    private DatePicker datePicker = new DatePicker();
    private ComboBox<String> departure = new ComboBox<>();
    private ComboBox<String> destination = new ComboBox<>();
    private RouteDAO rute = new RouteDAO();
    private Button searchRoutes = new Button("Search");
    private Button nextPage = new Button("Continue",new Icon(VaadinIcon.EXIT));
    private Grid<Rute> grid;
    public BuyView(){
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        HttpSession session = req.getSession();
        try{
            User currentUser = (User) session.getAttribute("user");
            if(!currentUser.getRole().equals("User")) throw new Exception();
            configVisualization();
            buttonConfig();
        }catch(Exception e){
            Notification.show("Please login as an user first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }
    }
    public void configVisualization(){
        datePicker.setPlaceholder("Pick a date");
        departure.setPlaceholder("Departure");
        departure.setItems(rute.getDepartures());
        destination.setPlaceholder("Destination");
        destination.setItems(rute.getDestinations());
        add(title,progressBar,datePicker,departure,destination,searchRoutes);
        gridConfig();
    }
    public void gridConfig(){
        grid = new Grid<>();
        remove(grid);
        grid.addColumn(Rute::getData).setHeader("Date");
        grid.addColumn(Rute::getPlecare).setHeader("Departure");
        grid.addColumn(Rute::getDestinatie).setHeader("Destination");
        grid.addColumn(Rute::getTarif).setHeader("Cost");
        grid.addColumn(Rute::getVehicleNumber).setHeader("Vehicle's Number");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.getColumns().forEach(col->col.setAutoWidth(true));
        add(grid);
    }
    public void buttonConfig(){
        searchRoutes.addClickListener(e->{
            if(rute.getSearch(departure.getValue(),destination.getValue()).size()==0){
                Notification.show("There doesn't exist a route with that destination and departure", 3000, Notification.Position.TOP_CENTER);
                grid.setItems(rute.getSearch(departure.getValue(),destination.getValue()));

            }else{
                grid.setItems(rute.getSearch(departure.getValue(),destination.getValue()));
                nextPage.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
                add(nextPage);
            }
        });
            nextPage.addClickListener(e->{
            if(getSelected()==null){
                Notification.show("Please select a route from the list below", 3000, Notification.Position.TOP_CENTER);
            }else{
                HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
                HttpSession session = req.getSession();
                session.setAttribute("selectedRoute",getSelected());
                UI.getCurrent().navigate("optionUser");
            }
        });
    }
    public Rute getSelected(){
        SingleSelect<Grid<Rute>,Rute> selection = grid.asSingleSelect();
        return selection.getValue();
    }

}

