/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author Chamali
 */
public class DbConnectionManager {
    private static DbConnectionManager connectionManager = null;
    private Connection connection;
    
    private String dbName = "payroll_db";
    private String user = "root";
    private String password = "asdf1234";
    private String url = "jdbc:mysql://localhost";
        
    private DbConnectionManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            
            String createQuery = "CREATE DATABASE IF NOT EXISTS " + dbName;
            String useQuery = "USE " + dbName;
            
            Statement statement = connection.createStatement();
            statement.addBatch(createQuery);
            statement.addBatch(useQuery);

            statement.executeBatch();
            
            System.out.println("Database connection successful");
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static DbConnectionManager shared() {
        if (connectionManager == null)
            connectionManager = new DbConnectionManager();
          
        return connectionManager;
    }
    
    public void createTablesIfNeeded() {
        createLoginTableIfNeeded();
        createEmployeeTableIfNeeded();
        createTimeLogTableIfNeeded();
    }
    
    public Connection getConnection() {
        return this.connection;
    }
    
    private boolean doesTableExist(String tableName) {
        String query = String.format("SHOW TABLES LIKE '%s'", tableName);
        
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            
            return statement.getResultSet().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private void createLoginTableIfNeeded() {
        if (!doesTableExist(TableName.login)) {
            String createQuery = "CREATE TABLE " + TableName.login + " (\n"
                + "username varchar(255) PRIMARY KEY, \n"
                + "password varchar(255) NOT NULL, \n"
                + "role varchar(255) NOT NULL, \n"
                + "employeeId INT);";
            
            String password = Helper.encryptPassword("Admin@password1");
            String insertQuery = "INSERT INTO " + TableName.login + " \n"
                    +"(username, password, role) \n" 
                    +"VALUES ('admin', '" + password + "', 'admin');";
        
            try {
                Statement statement = connection.createStatement();
                statement.addBatch(createQuery);
                statement.addBatch(insertQuery);
                
                statement.executeBatch();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
  
    private void createEmployeeTableIfNeeded() {
        if (!doesTableExist(TableName.employee)) {
            String createQuery = "CREATE TABLE " + TableName.employee + " (\n"
                + "employeeId INT NOT NULL AUTO_INCREMENT, \n"
                + "name varchar(255) NOT NULL, \n"
                + "address varchar(255) NOT NULL, \n"
                + "mobile varchar(255) NOT NULL, \n"
                + "email varchar(255) NOT NULL, \n"
                + "accountNumber varchar(255) NOT NULL, \n"
                + "designation varchar(255) NOT NULL, \n"
                + "hourlyRate double NOT NULL, \n"
                + "taxRate double NOT NULL, \n"
                + "superRate double NOT NULL, \n"
                + "dob date NOT NULL, \n"
                + "PRIMARY KEY (employeeId));";
            
            String incrementQuery = "ALTER TABLE " + TableName.employee + " AUTO_INCREMENT = 1001";
            
            String fkConstraint = "ALTER TABLE " + TableName.login + " \n" +
                "ADD CONSTRAINT FK_Employee_employeeId FOREIGN KEY (employeeId) \n" +
                "REFERENCES "+ TableName.employee + "(employeeId);";
        
            try {
                Statement statement = connection.createStatement();
                statement.addBatch(createQuery);
                statement.addBatch(incrementQuery);
                statement.addBatch(fkConstraint);
                
                statement.executeBatch();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    private void createTimeLogTableIfNeeded() {
        if (!doesTableExist(TableName.timeLog)) {
            String createQuery = "CREATE TABLE " + TableName.timeLog + " (\n"
                + "logId INT NOT NULL AUTO_INCREMENT, \n"
                + "date date NOT NULL, \n"
                + "hours double NOT NULL, \n"
                + "employeeId Int NOT NULL, \n"
                + "PRIMARY KEY (logId));";
            
            String fkConstraint = "ALTER TABLE " + TableName.timeLog + " \n" +
                "ADD CONSTRAINT FK_TimeLog_employeeId FOREIGN KEY (employeeId) \n" +
                "REFERENCES "+ TableName.employee + "(employeeId);";
        
            try {
                Statement statement = connection.createStatement();
                statement.addBatch(createQuery);
                statement.addBatch(fkConstraint);
                
                statement.executeBatch();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
