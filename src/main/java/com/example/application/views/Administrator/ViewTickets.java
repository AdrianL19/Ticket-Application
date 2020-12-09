package com.example.application.views.Administrator;

import com.example.application.ConnectionFactory.BileteDAO;
import com.example.application.Model.Bilet;
import com.example.application.Model.User;
import com.example.application.views.User.MainViewUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Route(value = "ticketsAdmin", layout = MainViewAdministrator.class)
@PageTitle("History")
public class ViewTickets extends VerticalLayout {
    private Button viewHistory = new Button("View purchases");
    private Grid<Bilet> grid;
    private BileteDAO bilete = new BileteDAO();
    private User currentUser;
    private H4 title = new H4("");
    private float sum = 0;

    public ViewTickets(){
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        HttpSession session = req.getSession();
        try{
            currentUser = (User) session.getAttribute("user");
            if(!currentUser.getRole().equals("Administrator")) throw new Exception();
            configVisualization();
            gridConfig();
            buttonConfig();
        }catch(Exception e){
            Notification.show("Please login as an administrator first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }
    }

    private void buttonConfig() {
        viewHistory.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        viewHistory.addClickListener(e->grid.setItems(bilete.viewBilete()));
    }
    public void gridConfig(){
        grid = new Grid<>();
        remove(grid);
        grid.addColumn(Bilet::getId).setHeader("Ticket ID");
        grid.addColumn(Bilet::getBuyer).setHeader("Buyer username");
        grid.addColumn(Bilet::getData).setHeader("Date of route");
        grid.addColumn(Bilet::getDestinatie).setHeader("Destination");
        grid.addColumn(Bilet::getPlecare).setHeader("Departure");
        grid.addColumn(Bilet::getTarif).setHeader("Cost");
        grid.addColumn(Bilet::getVehicleNumber).setHeader("Vehicle number");
        grid.addColumn(Bilet::getUsername).setHeader("Driver's username");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.getColumns().forEach(col->col.setAutoWidth(true));
        setSizeFull();
        add(grid);
    }
    private void configVisualization() {
        add(new H1("Ticket History"));
        List<Bilet> temp = bilete.viewBilete();
        for(Bilet i : temp){
            sum+= i.getTarif();
        }
        title.setText("Number of tickets sold "+ temp.size() +" for a total amount of "+ sum + " RON");
        add(title);
        add(viewHistory);
    }
}
