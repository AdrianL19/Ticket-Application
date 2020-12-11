package com.example.application.Model;

public class Bilet {
    private int id;
    private String data;
    private String destinatie;
    private String plecare;
    private String oraPlecare;
    private String oraDestinatie;
    private float tarif;
    private String vehicleNumber;
    private String username;

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    private String buyer;


    public String getOraPlecare() {
        return oraPlecare;
    }

    public void setOraPlecare(String oraPlecare) {
        this.oraPlecare = oraPlecare;
    }

    public String getOraDestinatie() {
        return oraDestinatie;
    }

    public void setOraDestinatie(String oraDestinatie) {
        this.oraDestinatie = oraDestinatie;
    }

    public Bilet(int id, String data, String destinatie, String plecare, float tarif, String vehicleNumber, String username, String buyer, String orad, String orap) {
        this.id = id;
        this.data = data;
        this.destinatie = destinatie;
        this.plecare = plecare;
        this.tarif = tarif;
        this.vehicleNumber = vehicleNumber;
        this.username = username;
        this.buyer=buyer;
        this.oraDestinatie=orad;
        this.oraPlecare=orap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDestinatie() {
        return destinatie;
    }

    public void setDestinatie(String destinatie) {
        this.destinatie = destinatie;
    }

    public String getPlecare() {
        return plecare;
    }

    public void setPlecare(String plecare) {
        this.plecare = plecare;
    }

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
