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
    public void insertSchedule(Schedule ex){
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("insert into orar(username,dataOrar,oraStart,oraEnd) values (?,?,?,?)");
            statement.setString(1,ex.getUsername());
            statement.setString(2,ex.getDate());
            statement.setString(3,ex.getTimeStart());
            statement.setString(4,ex.getTimeEnd());
            statement.executeUpdate();

        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }

    }
    public void deleteUser(String username){
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            String[] split = username.split(" ");
            PreparedStatement statement = dbConnection.prepareStatement("delete from orar where username = ? and dataOrar = ? and oraStart = ? and oraEnd = ?");
            System.out.println(split[0]+split[1]+split[2]+split[3]);
            statement.setString(1,split[0]);
            statement.setString(2,split[1]);
            statement.setString(3,split[2]);
            statement.setString(4,split[3]);
            statement.executeUpdate();

        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }

    }
}
