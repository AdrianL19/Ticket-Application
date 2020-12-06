package com.example.application.Model;

import java.util.Objects;

public class Rute {
    private String destinatie;
    private String plecare;
    private float tarif;
    private String vehicleNumber;

    public Rute(String destinatie, String plecare, float tarif, String vehicleNumber) {
        this.destinatie = destinatie;
        this.plecare = plecare;
        this.tarif = tarif;
        this.vehicleNumber = vehicleNumber;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rute rute = (Rute) o;
        return Float.compare(rute.tarif, tarif) == 0 &&
                Objects.equals(destinatie, rute.destinatie) &&
                Objects.equals(plecare, rute.plecare) &&
                Objects.equals(vehicleNumber, rute.vehicleNumber);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
