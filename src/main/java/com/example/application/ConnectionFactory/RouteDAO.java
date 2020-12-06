package com.example.application.ConnectionFactory;

import com.example.application.Model.Route;
import com.example.application.Model.Vehicle;
import com.vaadin.flow.component.notification.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO {
    List<Route> routes = new ArrayList<>();
    public List<Route> getVehicles(){
        routes.clear();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from vehicle");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String destinatie = result.getString("destinatie");
                String plecare = result.getString("plecare");
                float tarif = result.getFloat("tarif");
                String vehicleNumber = result.getString("vehicleNumber");
                Route temp = new Route(destinatie,plecare,tarif,vehicleNumber);
                routes.add(temp);
            }
        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
        return routes;
    }
    public void insertVehicle(Route temp){
        boolean verify = true;
        for(Route i : this.getVehicles()){
            if(i.equals(temp)){
                verify=false;
            }
        }
        if(verify){
            Connection dbConnection = ConnectionFactory.getConnection();
            try {
                PreparedStatement statement = dbConnection.prepareStatement("insert into rute(destiantie,plecare,tarif,vehicleNumber) values (?,?,?,?)");
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

        }else{
            Notification.show("Route already exists!");
        }
    }
    public void deleteVehicle(Route temp){

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
