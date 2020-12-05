package com.example.application.Model;

public class Vehicle {
    private int id;
    private String number;
    private int numberOfSlots;
    private String driver;

    public Vehicle(int id, String number, int numberOfSlots, String driver) {
        this.id = id;
        this.number = number;
        this.numberOfSlots = numberOfSlots;
        this.driver = driver;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getNumberOfSlots() {
        return numberOfSlots;
    }

    public void setNumberOfSlots(int numberOfSlots) {
        this.numberOfSlots = numberOfSlots;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
