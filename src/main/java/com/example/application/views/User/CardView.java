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
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Route(value = "buyCard")
@PageTitle("Buy Ticket")
public class CardView extends VerticalLayout {
    private TextField creditCardNumber = new TextField("Credit card number");
    private TextField titularCard = new TextField("Name on card");
    private ComboBox<Integer> month = new ComboBox<>();
    private ComboBox<Integer> year = new ComboBox<>();
    private TextField securityCode = new TextField("CVV");
    private Button sumbit = new Button("Submit");
    private User currentUser;
    private Rute currentRoute;
    private RouteDAO route = new RouteDAO();
    private VehicleDAO vehicle = new VehicleDAO();
    private BileteDAO bilete = new BileteDAO();
    private String currentDriver;
    private float pret;
    private int numberofTickets;
    private HttpServletRequest req = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
    private HttpSession session = req.getSession();

    public CardView() {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        try {
            currentUser = (User) session.getAttribute("user");
            if (!currentUser.getRole().equals("User")) throw new Exception();
            numberofTickets = (int) session.getAttribute("number");
            currentRoute = (Rute) session.getAttribute("selectedRoute");
            pret = numberofTickets * currentRoute.getTarif();
            remainingSlots();
            configVisualization();


        } catch (Exception e) {
            Notification.show("Please login as an user first!", 3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("http://localhost:8080/");
        }

    }

    private void configVisualization() {
        add(new H1("Enter card information below:"));
        creditCardNumber.setPlaceholder("XXXX XXXX XXXX XXXX");
        titularCard.setPlaceholder("eg. Popescu Ion");
        securityCode.setPlaceholder("Card print");
        add(creditCardNumber, titularCard);
        HorizontalLayout layout = new HorizontalLayout();
        month.setLabel("Expiration");
        month.setPlaceholder("Month");
        month.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        year.setLabel("Date");
        year.setPlaceholder("Year");
        year.setItems(2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030);
        layout.add(month, year);
        add(securityCode);

        add(layout);
        sumbit.addClickListener(e -> {
            if (!creditCardNumber.getValue().equals("") && verifyCardNumber(creditCardNumber.getValue())) {
                if (!titularCard.getValue().equals("") && verifyName(titularCard.getValue())) {
                    if (month.getValue() != null && year.getValue() != null) {
                        if (!securityCode.getValue().equals("") && verifySecurityCode(securityCode.getValue())) {
                            route.updateSlots(currentRoute, numberofTickets);
                            for (int i = 0; i < numberofTickets; i++) {
                                Bilet temp = new Bilet(0, currentRoute.getData(), currentRoute.getDestinatie(), currentRoute.getPlecare(), currentRoute.getTarif(), currentRoute.getVehicleNumber(), currentDriver, currentUser.getUsername());
                                bilete.insertBilet(temp);
                            }
                            Notification.show("Your command has been sent. Check email for confirmation.", 10000, Notification.Position.TOP_CENTER);
                            UI.getCurrent().navigate("welcomeUser");
                        } else {
                            Notification.show("Please enter a valid security code", 3000, Notification.Position.TOP_CENTER);
                        }
                    } else {
                        Notification.show("Please enter Expiration Date", 3000, Notification.Position.TOP_CENTER);
                    }
                } else {
                    Notification.show("Please enter  a valid name on card", 3000, Notification.Position.TOP_CENTER);
                }
            } else {
                Notification.show("Please enter credit a valid card number", 3000, Notification.Position.TOP_CENTER);
            }
        });
        add(new H3("Sum to pay " + pret + " RON"));
        sumbit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(sumbit);
    }

    public boolean verifyCardNumber(String card) {
        String card1 = card.replace(" ", "");
        if (card1.length() != 16) {
            return false;
        }
        for (int i = 0; i < card1.length(); i++) {
            if (!Character.isDigit(card1.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean verifySecurityCode(String card) {
        if (card.length() != 3) {
            return false;
        }
        for (int i = 0; i < card.length(); i++) {
            if (!Character.isDigit(card.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean verifyName(String card) {
        try {
            String[] split = card.split(" ");
            if (split.length == 1) {
                return false;
            }
            for (String i : split) {
                for (int j = 0; j < i.length(); j++) {
                    if (!Character.isLetter(i.charAt(j))) return false;
                    if (j == 0) {
                        if (!Character.isUpperCase(i.charAt(j)) && Character.isLetter(i.charAt(j))) {
                            return false;
                        }
                    } else {
                        if (Character.isUpperCase(i.charAt(j)) && Character.isLetter(i.charAt(j))) {
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void remainingSlots() {
        for (Vehicle i : vehicle.getVehicles()) {
            currentRoute = (Rute) session.getAttribute("selectedRoute");
            if (currentRoute.getVehicleNumber().equals(i.getNumber())) {
                currentDriver = i.getDriver();
            }
        }
    }
}
