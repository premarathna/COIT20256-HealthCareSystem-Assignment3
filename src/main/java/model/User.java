/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import Utils.DbConnectionManager;
import Utils.Helper;
import Utils.TableName;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Chamali
 */
public class User {
    private String name;
    private String phone;
    private String email;
    private String username;
    private String role;
    private String encryptedPassword; // Encoded password
    private int userId;
    
    public User(String username) {
        this.username = username;
        this.fetchUserDetails();
    }
 
    public User(
            int userId,
            String name, 
            String phone, 
            String email, 
            String usernane, 
            String decryptedPassword,
            String role
    ) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.username = usernane;
        this.encryptedPassword = Helper.encryptPassword(decryptedPassword);
        this.role = role;
        this.userId = userId;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getname() {
        return this.name;
    }
    
    public int getUserId() {
        return this.userId;
    }
    
    
    public String getEncryptedPassword() {
        return this.encryptedPassword;
    }
    
    public String getDecryptedPassword() {
        return Helper.decryptPassword(this.encryptedPassword);
    }
    
    public String getRole() {
        return this.role;
    }

    private void fetchUserDetails() {
        User userModel = null;
        String query = "SELECT * from " + TableName.user
                + " WHERE username = '" + username + "';";
        
        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            
            while (results.next()) {
                this.role = results.getString("role");
                this.encryptedPassword = results.getString("password");
            }  
        } catch (SQLException e) {
             System.out.println(e.getMessage());
        }
    }
    
    public boolean insertUser() {
        String insertQuery = "INSERT INTO " + TableName.user + "\n"
         +"(name,phone,email,username, password, role)\n"
         +"VALUES('" + this.name + "', \n"
         +"'" + this.phone + "', \n"
         +"'" + this.email + "', \n"
         +"'" + this.username + "', \n"
         +"'" + this.encryptedPassword + "', \n"
            +"'" + this.role +"');";
        
        try {
            Statement statement = DbConnectionManager.shared().getConnection().createStatement();
            statement.execute(insertQuery);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
