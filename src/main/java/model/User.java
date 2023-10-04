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
    private String username;
    private String role;
    private String encryptedPassword; // Encoded password
    private int employeeId;
    
    public User(String username) {
        this.username = username;
        this.fetchUserDetails();
    }
    
    public User(
            String usernane, 
            String decryptedPassword,
            String role
    ) {
        this.username = usernane;
        this.encryptedPassword = Helper.encryptPassword(decryptedPassword);
        this.role = role;
    }
    
    public User(
            String usernane, 
            String encryptedPassword,
            String role,
            int employeeId
    ) {
        this.username = usernane;
        this.encryptedPassword = encryptedPassword;
        this.role = role;
        this.employeeId = employeeId;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public int getEmployeeId() {
        return this.employeeId;
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
    
    public void setEmployeeId(int id) {
        this.employeeId = id;
    }
    
    private void fetchUserDetails() {
        User userModel = null;
        String query = "SELECT * from " + TableName.login
                + " WHERE username = '" + username + "';";
        
        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            
            while (results.next()) {
                this.role = results.getString("role");
                this.encryptedPassword = results.getString("password");
                this.employeeId = results.getInt("employeeId");
            }  
        } catch (SQLException e) {
             System.out.println(e.getMessage());
        }
    }
    
    public void insertUser() {
           String insertQuery = "INSERT INTO " + TableName.login + "\n"
            +"(username, password, role, employeeId)\n"
            +"VALUES('" + this.username + "', \n"
            +"'" + this.encryptedPassword + "', \n"
            +"'" + this.role + "', \n"      
            +"'" + this.employeeId + "');";
        
        try {
            Statement statement = DbConnectionManager.shared().getConnection().createStatement();
            statement.execute(insertQuery);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
