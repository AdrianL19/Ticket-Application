package com.example.application.ConnectionFactory;

import com.example.application.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
    List<User> users = new ArrayList<>();
    public List<User> getUsers(){
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from users");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String username= result.getString("username");
                String password = result.getString("passwordUser");
                String role = result.getString("role");
                users.add(new User(username,password,role));
            }
        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
        return users;
    }
    public void insertUser(User ex){
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("insert into users(username,passwordUser,role) values (?,?,?)");
            statement.setString(1,ex.getUsername());
            statement.setString(2,ex.getPassword());
            statement.setString(3,ex.getRole());
            statement.executeUpdate();

        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }

    }
}
