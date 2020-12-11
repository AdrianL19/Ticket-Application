package com.example.application.Model;

import java.util.Objects;

public class Rute {
    private String data;
    private String destinatie;
    private String plecare;
    private String oraPlecare;
    private String oraAjungere;
    private float tarif;
    private String vehicleNumber;
    private int numberofSlots;

    public String getOraPlecare() {
        return oraPlecare;
    }

    public void setOraPlecare(String oraPlecare) {
        this.oraPlecare = oraPlecare;
    }

    public String getOraAjungere() {
        return oraAjungere;
    }

    public void setOraAjungere(String oraAjungere) {
        this.oraAjungere = oraAjungere;
    }

    public Rute(String data, String destinatie, String plecare, float tarif, String vehicleNumber, int number, String oraAjungere, String oraPlecare ) {
        this.data=data;
        this.destinatie = destinatie;
        this.plecare = plecare;
        this.tarif = tarif;
        this.vehicleNumber = vehicleNumber;
        this.numberofSlots = number;
        this.oraAjungere=oraAjungere;
        this.oraPlecare=oraPlecare;
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



    @Override
    public int hashCode() {
        return 0;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getNumberofSlots() {
        return numberofSlots;
    }

    public void setNumberofSlots(int numberofSlots) {
        this.numberofSlots = numberofSlots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rute rute = (Rute) o;
        return Float.compare(rute.tarif, tarif) == 0 &&
                numberofSlots == rute.numberofSlots &&
                Objects.equals(data, rute.data) &&
                Objects.equals(destinatie, rute.destinatie) &&
                Objects.equals(plecare, rute.plecare) &&
                Objects.equals(oraPlecare, rute.oraPlecare) &&
                Objects.equals(oraAjungere, rute.oraAjungere) &&
                Objects.equals(vehicleNumber, rute.vehicleNumber);
    }
}
