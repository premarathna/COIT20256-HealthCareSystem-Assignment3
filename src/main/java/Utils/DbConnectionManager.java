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

    private String dbName = "hospital_db";
    private String user = "root";
    private String password = "asdf1234";//asdf1234
    private String url = "jdbc:mysql://localhost";

    public DbConnectionManager() {
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
        if (connectionManager == null) {
            connectionManager = new DbConnectionManager();
        }

        return connectionManager;
    }

    public void createTablesIfNeeded() {
        createPatientTableIfNeeded();
        createUserTableIfNeeded();
        createPatientReportTableIfNeeded();
        createAppoinmentReportTableIfNeeded();
        createInvoiceReportTableIfNeeded();
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

    private void createUserTableIfNeeded() {
        if (!doesTableExist(TableName.user)) {
            String createQuery = "CREATE TABLE " + TableName.user + " (\n"
                    + "userId INT NOT NULL AUTO_INCREMENT, \n"
                    + "name varchar(255), \n"
                    + "phone varchar(255), \n"
                    + "email varchar(255), \n"
                    + "username varchar(255) NOT NULL, \n"
                    + "password varchar(255) NOT NULL, \n"
                    + "role varchar(255) NOT NULL, \n"
                    + "PRIMARY KEY (userId));";

            String incrementQuery = "ALTER TABLE " + TableName.user + " AUTO_INCREMENT = 001";

            try {
                Statement statement = connection.createStatement();
                statement.addBatch(createQuery);
                //statement.addBatch(insertQuery);

                statement.executeBatch();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void createPatientTableIfNeeded() {
        if (!doesTableExist(TableName.patient)) {
            String createQuery = "CREATE TABLE " + TableName.patient + " (\n"
                    + "patientId INT NOT NULL AUTO_INCREMENT, \n"
                    + "fName varchar(255) NOT NULL, \n"
                    + "lName varchar(255) NOT NULL, \n"
                    + "address varchar(255) NOT NULL, \n"
                    + "mobile varchar(255) NOT NULL, \n"
                    + "email varchar(255) NOT NULL, \n"
                    + "gender varchar(255) NOT NULL, \n"
                    + "insuaranceId varchar(255) NOT NULL, \n"
                    + "dob date NOT NULL, \n"
                    + "PRIMARY KEY (patientId));";

            String incrementQuery = "ALTER TABLE " + TableName.patient + " AUTO_INCREMENT = 1001";

            String fkConstraint = "ALTER TABLE " + TableName.login + " \n"
                    + "ADD CONSTRAINT FK_Patient_patientId FOREIGN KEY (patientId) \n"
                    + "REFERENCES " + TableName.patient + "(patientId);";

            try {
                Statement statement = connection.createStatement();
                statement.addBatch(createQuery);
                statement.addBatch(incrementQuery);
                //statement.addBatch(fkConstraint);

                statement.executeBatch();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void createPatientReportTableIfNeeded() {
        if (!doesTableExist(TableName.patient_report)) {
            String createQuery = "CREATE TABLE " + TableName.patient_report + " (\n"
                    + "patientReportId INT NOT NULL AUTO_INCREMENT, \n"
                    + "fName varchar(255) NOT NULL, \n"
                    + "lName varchar(255) NOT NULL, \n"
                    + "phone varchar(255) NOT NULL, \n"
                    + "email varchar(255) NOT NULL, \n"
                    + "address varchar(255) NOT NULL, \n"
                    + "dateCreated varchar(255) NOT NULL, \n"
                    + "insuranceId varchar(255) NOT NULL, \n"
                    + "patientId INT NOT NULL, \n"
                    + "PRIMARY KEY (patientReportId));";

            String incrementQuery = "ALTER TABLE " + TableName.patient_report + " AUTO_INCREMENT = 2001";

            try {
                Statement statement = connection.createStatement();
                statement.addBatch(createQuery);
                statement.addBatch(incrementQuery);
                //statement.addBatch(fkConstraint);

                statement.executeBatch();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void createAppoinmentReportTableIfNeeded() {
        if (!doesTableExist(TableName.appoinment_report)) {
            String createQuery = "CREATE TABLE " + TableName.appoinment_report + " (\n"
                    + "appoinmentReportId INT NOT NULL AUTO_INCREMENT, \n"
                    + "appoinmentId INT NOT NULL, \n"
                    + "date varchar(255) NOT NULL, \n"
                    + "time varchar(255) NOT NULL, \n"
                    + "dateCreated varchar(255) NOT NULL, \n"
                    + "patientFName varchar(255) NOT NULL, \n"
                    + "patientLName varchar(255) NOT NULL, \n"
                    + "doctorName varchar(255) NOT NULL, \n"
                    + "PRIMARY KEY (appoinmentReportId));";

            String incrementQuery = "ALTER TABLE " + TableName.appoinment_report + " AUTO_INCREMENT = 3001";

            try {
                Statement statement = connection.createStatement();
                statement.addBatch(createQuery);
                statement.addBatch(incrementQuery);
                //statement.addBatch(fkConstraint);

                statement.executeBatch();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void createInvoiceReportTableIfNeeded() {
        if (!doesTableExist(TableName.invoice_report)) {
            String createQuery = "CREATE TABLE " + TableName.invoice_report + " (\n"
                    + "invoiceReportId INT NOT NULL AUTO_INCREMENT, \n"
                    + "invoiceId INT NOT NULL, \n"
                    + "date varchar(255) NOT NULL, \n"
                    + "time varchar(255) NOT NULL, \n"
                    + "dateCreated varchar(255) NOT NULL, \n"
                    + "appointmentId INT NOT NULL, \n"
                    + "doctorName varchar(255) NOT NULL, \n"
                    + "patientFName varchar(255) NOT NULL, \n"
                    + "patientLName varchar(255) NOT NULL, \n"
                    + "PRIMARY KEY (appoinmentReportId));";

            String incrementQuery = "ALTER TABLE " + TableName.invoice_report + " AUTO_INCREMENT = 4001";

            try {
                Statement statement = connection.createStatement();
                statement.addBatch(createQuery);
                statement.addBatch(incrementQuery);
                //statement.addBatch(fkConstraint);

                statement.executeBatch();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
