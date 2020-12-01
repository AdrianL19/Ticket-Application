package com.example.application.ConnectionFactory;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/db";
    private static final String USER = "root";
    private static final String PASS = "Borcalucian19#";
    /**
     * This method is the constructor of the ConnectionFactory class.
     * It also connects to the database through the driver.
     */
    private ConnectionFactory(){

        try{
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e){
            LOGGER.log(Level.WARNING , "Error! Database couldn't be loaded.");
            e.printStackTrace();
        }
    }
    /**
     * This method is used to connect to the database
     * @return connection to the database
     */
    public static Connection getConnection(){

        Connection connection = null;
        try{
            connection = DriverManager.getConnection(DBURL,USER,PASS);
        }catch (SQLException e){
            LOGGER.log(Level.WARNING, "Error! Failed to connect to database."+ e.toString());
        }
        return connection;
    }
    /**
     * This method closes a connection to the database
     * @param connection connection you want to be closed
     */
    public static void close(Connection connection){

        if(connection!=null){
            try{
                connection.close();
            }catch (SQLException e){
                LOGGER.log(Level.WARNING, "Error! Connection couldn't close");
            }
        }
    }
}
