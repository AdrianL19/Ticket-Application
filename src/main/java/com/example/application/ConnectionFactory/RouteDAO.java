package com.example.application.ConnectionFactory;

import com.example.application.Model.Rute;
import com.vaadin.flow.component.notification.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO {
    List<Rute> rutes = new ArrayList<>();
    public List<Rute> getRoutes(){
        rutes.clear();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from rute");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String data = result.getString("data");
                String destinatie = result.getString("destinatie");
                String plecare = result.getString("plecare");
                float tarif = result.getFloat("tarif");
                String vehicleNumber = result.getString("vehicleNumber");
                Rute temp = new Rute(data,destinatie,plecare,tarif,vehicleNumber);
                rutes.add(temp);
            }
        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
        return rutes;
    }
    public void insertRoute(Rute temp){
        boolean verify = true;
        for(Rute i : this.getRoutes()){
            if(i.equals(temp)){
                verify=false;
            }
        }
        if(verify){
            Connection dbConnection = ConnectionFactory.getConnection();
            try {
                PreparedStatement statement = dbConnection.prepareStatement("insert into rute(data,destinatie,plecare,tarif,vehicleNumber) values (?,?,?,?,?)");
                statement.setString(1, temp.getData());
                statement.setString(2,temp.getDestinatie());
                statement.setString(3,temp.getPlecare());
                statement.setFloat(4,temp.getTarif());
                statement.setString(5,temp.getVehicleNumber());
                statement.executeUpdate();

            }catch ( SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                ConnectionFactory.close(dbConnection);
            }

        }else{
            Notification.show("Route already exists!");
        }
    }
    public void deleteRoute(Rute temp){

        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("delete from rute where destinatie = ? and plecare = ? and tarif = ? and vehicleNumber = ?");
            statement.setString(1,temp.getDestinatie());
            statement.setString(2,temp.getPlecare());
            statement.setFloat(3,temp.getTarif());
            statement.setString(4,temp.getVehicleNumber());
            statement.executeUpdate();

        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }

    }
}
