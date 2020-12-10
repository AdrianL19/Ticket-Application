package com.example.application.ConnectionFactory;

import com.example.application.Model.Bilet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BileteDAO {
    private List<Bilet> list = new ArrayList<>();
    public void insertBilet(Bilet temp){
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("insert into bilete(data,destinatie,plecare,tarif,vehicleNumber,username,buyer) values (?,?,?,?,?,?,?)");
            statement.setString(1, temp.getData());
            statement.setString(2,temp.getDestinatie());
            statement.setString(3,temp.getPlecare());
            statement.setFloat(4,temp.getTarif());
            statement.setString(5,temp.getVehicleNumber());
            statement.setString(6,temp.getUsername());
            statement.setString(7,temp.getBuyer());
            statement.executeUpdate();

        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
    }
    public List<Bilet> viewBilete(){
        list.clear();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from bilete");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String data = result.getString("data");
                String destinatie = result.getString("destinatie");
                String plecare = result.getString("plecare");
                float tarif = result.getFloat("tarif");
                String vehicleNumber = result.getString("vehicleNumber");
                String driverusername = result.getString("username");
                String buyer = result.getString("buyer");
                Bilet temp = new Bilet(id,data,destinatie,plecare,tarif,vehicleNumber,driverusername,buyer);
                list.add(temp);
            }

        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
        return list;
    }
    public List<Bilet> viewBiletebyUsername(String username){
        list.clear();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from bilete where buyer = ?");
            statement.setString(1,username);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String data = result.getString("data");
                String destinatie = result.getString("destinatie");
                String plecare = result.getString("plecare");
                float tarif = result.getFloat("tarif");
                String vehicleNumber = result.getString("vehicleNumber");
                String driverusername = result.getString("username");
                String buyer = result.getString("buyer");
                Bilet temp = new Bilet(id,data,destinatie,plecare,tarif,vehicleNumber,driverusername,buyer);
                list.add(temp);
            }

        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
        return list;
    }
    public List<Bilet> viewBiletebyNumber(String username,String vehicleNumberr,String dataa){
        list.clear();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from bilete where username = ? and vehicleNumber =? and data = ?");
            statement.setString(1,username);
            statement.setString(2,vehicleNumberr);
            statement.setString(3,dataa);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String data = result.getString("data");
                String destinatie = result.getString("destinatie");
                String plecare = result.getString("plecare");
                float tarif = result.getFloat("tarif");
                String vehicleNumber = result.getString("vehicleNumber");
                String driverusername = result.getString("username");
                String buyer = result.getString("buyer");
                Bilet temp = new Bilet(id,data,destinatie,plecare,tarif,vehicleNumber,driverusername,buyer);
                list.add(temp);
            }

        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
        return list;
    }
}
