package com.example.application.Model;

public class Schedule {
    private String username;
    private String date;
    private String timeStart;
    private String timeEnd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Schedule(String username, String date, String timeStart, String timeEnd) {
        this.username = username;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }
}
