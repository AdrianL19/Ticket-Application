package com.example.application.ConnectionFactory;

import com.example.application.Model.Bilet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BileteDAO {

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
}
