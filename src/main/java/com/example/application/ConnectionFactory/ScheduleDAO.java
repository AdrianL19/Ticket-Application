package com.example.application.ConnectionFactory;

import com.example.application.Model.Schedule;
import com.example.application.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    List<Schedule> schedule = new ArrayList<>();
    public List<Schedule> getUsers(){
        schedule.clear();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from orar");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String username= result.getString("username");
                String date = result.getString("dataOrar");
                String[] split = date.split(" ");
                String oraStart = result.getString("oraStart");
                String oraEnd = result.getString("oraEnd");
                schedule.add(new Schedule(username,split[0],oraStart,oraEnd));
            }
        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
        return schedule;
    }
    public List<Schedule> getScheduleDriver(String driver){
        schedule.clear();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from orar");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String username= result.getString("username");
                String date = result.getString("dataOrar");
                String[] split = date.split(" ");
                String oraStart = result.getString("oraStart");
                String oraEnd = result.getString("oraEnd");
                if(username.equals(driver)){
                    schedule.add(new Schedule(username,split[0],oraStart,oraEnd));
                }
            }
        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
        return schedule;
    }
}
