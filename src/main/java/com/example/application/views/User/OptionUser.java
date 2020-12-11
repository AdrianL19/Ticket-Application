package com.example.application.views.User;
import com.example.application.ConnectionFactory.BileteDAO;
import com.example.application.ConnectionFactory.RouteDAO;
import com.example.application.ConnectionFactory.VehicleDAO;
import com.example.application.Model.Bilet;
import com.example.application.Model.Rute;
import com.example.application.Model.User;
import com.example.application.Model.Vehicle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Route(value = "optionUser", layout = MainViewUser.class)
@PageTitle("Select Page")

public class OptionUser extends VerticalLayout {
    private ProgressBar progressBar = new ProgressBar();
    private H1 title = new H1("Option User");
    private H4 remainingSlots = new H4();
    private H3 stats = new H3();
    private VehicleDAO vehicle = new VehicleDAO();
    private RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
    private HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
    private HttpSession session = req.getSession();
    private NumberField numberField = new NumberField();
    private Button pay = new Button("Continue");
    private Button goback = new Button("Go back");
    private Rute currentRoute;
    private User currentUser;
    private String currentDriver;
    private BileteDAO bilete = new BileteDAO();
    private RouteDAO route = new RouteDAO();
    private HorizontalLayout layout = new HorizontalLayout();

    public OptionUser(){
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        try{
            currentUser = (User) session.getAttribute("user");
            if(!currentUser.getRole().equals("User")) throw new Exception();
            configVisualization();
            buttonConfig();


        }catch(Exception e){
            Notification.show("Please login as an user first!",3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }
    }
    public void configVisualization(){
        progressBar.setValue(0.33);
        radioGroup.setLabel("Payment/Book mode");
        radioGroup.setItems("Book route","Pay cash","Pay with Credit Card");
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        numberField.setHasControls(true);
        numberField.setStep(1.0);
        numberField.setMin(1);
        add(title);
        remainingSlots();
        add(layout);
        add(radioGroup);
    }
    public void buttonConfig(){
        HorizontalLayout layout = new HorizontalLayout();
        pay.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        goback.addThemeVariants(ButtonVariant.LUMO_ERROR);
        pay.addClickListener(e->{
           Span content = new Span("Are you sure you want to continue?");
           NativeButton buttonInside = new NativeButton("Yes");
           NativeButton buttonInside2 = new NativeButton("No");
           Notification notification = new Notification(content,buttonInside,buttonInside2);
           notification.setPosition(Notification.Position.TOP_CENTER);
           buttonInside2.addClickListener(event -> notification.close());
           buttonInside.addClickListener(event->{
                buyTicket();
                notification.close();
           });
           notification.open();

        });
        goback.addClickListener(e->{
            UI.getCurrent().navigate("buyZone");
        });
        layout.add(pay,goback);
        add(layout);
    }
    private void buyTicket() {
        if(numberField.getValue()!=null){
            if(radioGroup.getValue()!=null){
                switch (radioGroup.getValue()){
                    case "Book route":
                        if(numberField.getValue().intValue()<3 && numberField.getValue().intValue()<=currentRoute.getNumberofSlots()){
                            route.updateSlots(currentRoute,numberField.getValue().intValue());
                            for(int i = 0; i < numberField.getValue().intValue();i++){
                                Bilet temp = new Bilet(0, currentRoute.getData(), currentRoute.getDestinatie(), currentRoute.getPlecare(), currentRoute.getTarif(), currentRoute.getVehicleNumber(), currentDriver, currentUser.getUsername(),currentRoute.getOraAjungere(),currentRoute.getOraPlecare());
                                bilete.insertBilet(temp);
                            }
                            Notification.show("Your command has been sent. Check email for confirmation.", 10000, Notification.Position.TOP_CENTER);
                            UI.getCurrent().navigate("welcomeUser");
                        }else{
                            Notification.show("You can only book maximum 2 tickets or you are trying to buy more tickets that slots existing.", 3000, Notification.Position.TOP_CENTER);
                        }
                        break;
                    case "Pay cash":
                        if(numberField.getValue().intValue()<3 && numberField.getValue().intValue()<=currentRoute.getNumberofSlots()){
                            route.updateSlots(currentRoute,numberField.getValue().intValue());
                            for(int i = 0; i < numberField.getValue().intValue();i++){
                                Bilet temp = new Bilet(0, currentRoute.getData(), currentRoute.getDestinatie(), currentRoute.getPlecare(), currentRoute.getTarif(), currentRoute.getVehicleNumber(), currentDriver, currentUser.getUsername(),currentRoute.getOraAjungere(),currentRoute.getOraPlecare());                                bilete.insertBilet(temp);
                                bilete.insertBilet(temp);
                            }
                            Notification.show("Your command has been sent. Check email for confirmation.", 10000, Notification.Position.TOP_CENTER);
                            UI.getCurrent().navigate("welcomeUser");
                        }else{
                            Notification.show("You can only book maximum 2 tickets or you are trying to buy more tickets that slots existing", 3000, Notification.Position.TOP_CENTER);
                        }
                        break;
                    case "Pay with Credit Card":
                        if(numberField.getValue().intValue()<=currentRoute.getNumberofSlots()){
                            session.setAttribute("number",  numberField.getValue().intValue());
                            UI.getCurrent().navigate("buyCard");
                        }else{
                            Notification.show("You are trying to buy more tickets than existing slots for that route.");

                        }
                        break;
                    default: Notification.show("Something went wrong, please try again!",3000, Notification.Position.TOP_CENTER);
                    break;
                }
            }else{
                Notification.show("Please select a payment method", 3000, Notification.Position.TOP_CENTER);
            }
        }else{
            Notification.show("Please select the number of tickets", 3000, Notification.Position.TOP_CENTER);
        }



    }
    private void remainingSlots(){
        for(Vehicle i : vehicle.getVehicles()){
            currentRoute = (Rute) session.getAttribute("selectedRoute");
            if(currentRoute.getVehicleNumber().equals(i.getNumber())){
                stats.setText("Route from departure " + currentRoute.getPlecare() + " to destination " + currentRoute.getDestinatie() + " costs " + currentRoute.getTarif() +" RON.");
                remainingSlots.setText("Slots available for this route: " + currentRoute.getNumberofSlots());
                currentDriver=i.getDriver();
                numberField.setMax(currentRoute.getNumberofSlots());
                layout.add(new H4("Number of tickets"),numberField);
            }
        }
        add(stats);
        add(remainingSlots);
    }
}
