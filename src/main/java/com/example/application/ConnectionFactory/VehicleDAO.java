package com.example.application.ConnectionFactory;


import com.example.application.Model.Schedule;
import com.example.application.Model.Vehicle;
import com.example.application.Model.User;
import com.vaadin.flow.component.notification.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {
    List<Vehicle> vehicles = new ArrayList<>();
    public List<Vehicle> getVehicles(){
        vehicles.clear();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from vehicle");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String username = result.getString("username");
                String vehicleNumber = result.getString("vehicleNumber");
                int id = result.getInt("id");
                int numberofSlots = result.getInt("numberofPlaces");
                Vehicle temp = new Vehicle(id,vehicleNumber,numberofSlots,username);
                vehicles.add(temp);
            }
        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
        return vehicles;
    }
    public void insertVehicle(Vehicle temp){
        boolean verify = false;
        for(Vehicle i : this.getVehicles()){
            if(temp.getDriver().equals(i.getDriver())){
               verify=true;
            }
        }
        if(!verify){
            Connection dbConnection = ConnectionFactory.getConnection();
            try {
                PreparedStatement statement = dbConnection.prepareStatement("insert into vehicle(username,vehicleNumber,numberofPlaces) values (?,?,?)");
                statement.setString(1,temp.getDriver());
                statement.setString(2,temp.getNumber());
                statement.setInt(3,temp.getNumberOfSlots());
                statement.executeUpdate();

            }catch ( SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                ConnectionFactory.close(dbConnection);
            }

        }else{
            Notification.show("Driver "+temp.getDriver()+" is already allocated to a vehicle");
        }



    }
    public void deleteVehicle(String temp){

        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("delete from vehicle where vehicleNumber=?");
            statement.setString(1,temp);
            statement.executeUpdate();

        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }

    }
}
