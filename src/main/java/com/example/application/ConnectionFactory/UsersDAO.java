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
        users.clear();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from users");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("userID");
                String username= result.getString("username");
                String password = result.getString("passwordUser");
                String role = result.getString("role");
                String email = result.getString("email");
                users.add(new User(id,username,password,email,role));
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
            PreparedStatement statement = dbConnection.prepareStatement("insert into users(username,email,passwordUser,role) values (?,?,?,?)");
            statement.setString(1,ex.getUsername());
            statement.setString(2,ex.getEmail());
            statement.setString(3,ex.getPassword());
            statement.setString(4,ex.getRole());
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
            PreparedStatement statement = dbConnection.prepareStatement("delete from users where username = ?");
            statement.setString(1,username);
            statement.executeUpdate();

        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }

    }
    public void updateUsername(String update,String username){
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("update users set username = ? where username = ?");
            statement.setString(1,update);
            statement.setString(2,username);
            statement.executeUpdate();

        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
    }
    public void updatePassword(String update,String username){
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("update users set passwordUser = ? where username = ?");
            statement.setString(1,update);
            statement.setString(2,username);
            statement.executeUpdate();

        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
    }
    public void updateEmail(String update,String username){
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("update users set email = ? where username = ?");
            statement.setString(1,update);
            statement.setString(2,username);
            statement.executeUpdate();

        }catch ( SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
    }
    public void updateRole(String update,String username){
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement("update users set role = ? where username = ?");
            statement.setString(1,update);
            statement.setString(2,username);
            statement.executeUpdate();

        }catch ( SQLException throwables ) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(dbConnection);
        }
    }
}
