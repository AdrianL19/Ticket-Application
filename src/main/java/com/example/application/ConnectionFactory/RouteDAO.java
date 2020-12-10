package com.example.application.ConnectionFactory;

import com.example.application.Model.Rute;
import com.vaadin.flow.component.notification.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                int number = result.getInt("numberofPlaces");
                Rute temp = new Rute(data,destinatie,plecare,tarif,vehicleNumber,number);
                rutes.add(temp);
            }
        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
        return rutes;
    }
    public List<String> getDestinations(){
        List<String> temp = new ArrayList<>();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from rute ");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String destinatie = result.getString("destinatie");
                temp.add(destinatie);
            }
        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
        return temp.stream().distinct().collect(Collectors.toList());
    }
    public List<String> getDepartures(){
        List<String> temp = new ArrayList<>();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from rute ");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String plecare = result.getString("plecare");
                temp.add(plecare);
            }
        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
        return temp.stream().distinct().collect(Collectors.toList());
    }
    public List<Rute> getRoutesByDestination(String destination){
        rutes.clear();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from rute where destinatie = ?");
            statement.setString(1,destination);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String data = result.getString("data");
                String destinatie = result.getString("destinatie");
                String plecare = result.getString("plecare");
                float tarif = result.getFloat("tarif");
                String vehicleNumber = result.getString("vehicleNumber");
                int number = result.getInt("numberofPlaces");
                Rute temp = new Rute(data,destinatie,plecare,tarif,vehicleNumber,number);
                rutes.add(temp);
            }
        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
        return rutes;
    }
    public List<Rute> getRoutesByDeparture(String departure){
        rutes.clear();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from rute where plecare = ?");
            statement.setString(1,departure);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String data = result.getString("data");
                String destinatie = result.getString("destinatie");
                String plecare = result.getString("plecare");
                float tarif = result.getFloat("tarif");
                String vehicleNumber = result.getString("vehicleNumber");
                int number = result.getInt("numberofPlaces");
                Rute temp = new Rute(data,destinatie,plecare,tarif,vehicleNumber,number);
                rutes.add(temp);
            }
        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
        return rutes;
    }

    public List<Rute> getSearch(String departure,String destination){
        rutes.clear();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from rute where plecare = ? and destinatie = ?");
            statement.setString(1,departure);
            statement.setString(2,destination);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String data = result.getString("data");
                String destinatie = result.getString("destinatie");
                String plecare = result.getString("plecare");
                float tarif = result.getFloat("tarif");
                String vehicleNumber = result.getString("vehicleNumber");
                int number = result.getInt("numberofPlaces");
                Rute temp = new Rute(data,destinatie,plecare,tarif,vehicleNumber,number);
                rutes.add(temp);
            }
        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
        return rutes;
    }
    public List<Rute> getSearchbyDriver(String driver){
        rutes.clear();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from rute where vehicleNumber = ?");
            statement.setString(1,driver);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String data = result.getString("data");
                String destinatie = result.getString("destinatie");
                String plecare = result.getString("plecare");
                float tarif = result.getFloat("tarif");
                String vehicleNumber = result.getString("vehicleNumber");
                int number = result.getInt("numberofPlaces");
                Rute temp = new Rute(data,destinatie,plecare,tarif,vehicleNumber,number);
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
                PreparedStatement statement = dbConnection.prepareStatement("insert into rute(data,destinatie,plecare,numberofPlaces,tarif,vehicleNumber) values (?,?,?,?,?,?)");
                statement.setString(1, temp.getData());
                statement.setString(2,temp.getDestinatie());
                statement.setString(3,temp.getPlecare());
                statement.setInt(4,temp.getNumberofSlots());
                statement.setFloat(5,temp.getTarif());
                statement.setString(6,temp.getVehicleNumber());
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
    public void updateSlots(Rute ruta,int nr){
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("update rute set numberofPlaces = ?  where data = ? and destinatie = ? and plecare = ? and tarif = ? and vehicleNumber = ? and numberofPlaces =? " );
            statement.setInt(1,ruta.getNumberofSlots()-nr);
            statement.setString(2,ruta.getData());
            statement.setString(3,ruta.getDestinatie());
            statement.setString(4,ruta.getPlecare());
            statement.setFloat(5,ruta.getTarif());
            statement.setString(6, ruta.getVehicleNumber());
            statement.setInt(7,ruta.getNumberofSlots());
            statement.executeUpdate();

        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
    }
}
