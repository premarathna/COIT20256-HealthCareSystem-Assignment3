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
import java.util.ArrayList;
import java.util.List;
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
    private String password = "123123";//asdf1234
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
        createDoctorTableIfNeeded();
        createAppointmentTableIfNeeded();
        createTimeslotTableIfNeeded();
        createInvoiceTableIfNeed();
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
            statement.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            }
        }
    }

    
    private void createDoctorTableIfNeeded() {
        if (!doesTableExist(TableName.doctor)) {
            String createQuery = "CREATE TABLE " + TableName.doctor + " (\n"
                    + "doctorId INT NOT NULL AUTO_INCREMENT, \n"
                    + "userId INT NOT NULL, \n"
                    + "availability VARCHAR(255) NOT NULL, \n"
                    + "name VARCHAR(255), \n" // Add the 'name' column
                    + "PRIMARY KEY (doctorId)"
                    + ");";
            try {
                Statement statement = connection.createStatement();
                statement.addBatch(createQuery);
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

            String incrementQuery = "ALTER TABLE " + TableName.invoice_report + " AUTO_INCREMENT = 2001";

       try {
            Statement statement = connection.createStatement();
            statement.addBatch(createQuery);
            statement.addBatch(incrementQuery);
            statement.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            }
        }
    }

    private void createTimeslotTableIfNeeded() {
    if (!doesTableExist(TableName.timeslot)) {
        String createQuery = "CREATE TABLE " + TableName.timeslot + " (\n"
       + "timeslotId INT NOT NULL AUTO_INCREMENT, \n"
        + "doctorId INT NOT NULL, \n"
        + "monday1 BOOLEAN NOT NULL, \n"
        + "monday2 BOOLEAN NOT NULL, \n"
        + "monday3 BOOLEAN NOT NULL, \n"
        + "monday4 BOOLEAN NOT NULL, \n"
        + "tuesday1 BOOLEAN NOT NULL, \n"
        + "tuesday2 BOOLEAN NOT NULL, \n"
        + "tuesday3 BOOLEAN NOT NULL, \n"
        + "tuesday4 BOOLEAN NOT NULL, \n"
        + "wednesday1 BOOLEAN NOT NULL, \n"
        + "wednesday2 BOOLEAN NOT NULL, \n"
        + "wednesday3 BOOLEAN NOT NULL, \n"
        + "wednesday4 BOOLEAN NOT NULL, \n"
        + "thursday1 BOOLEAN NOT NULL, \n"
        + "thursday2 BOOLEAN NOT NULL, \n"
        + "thursday3 BOOLEAN NOT NULL, \n"
        + "thursday4 BOOLEAN NOT NULL, \n"
        + "friday1 BOOLEAN NOT NULL, \n"
        + "friday2 BOOLEAN NOT NULL, \n"
        + "friday3 BOOLEAN NOT NULL, \n"
        + "friday4 BOOLEAN NOT NULL, \n"
        + "saturday1 BOOLEAN NOT NULL, \n"
        + "saturday2 BOOLEAN NOT NULL, \n"
        + "saturday3 BOOLEAN NOT NULL, \n"
        + "saturday4 BOOLEAN NOT NULL, \n"
        + "sunday1 BOOLEAN NOT NULL, \n"
        + "sunday2 BOOLEAN NOT NULL, \n"
        + "sunday3 BOOLEAN NOT NULL, \n"
        + "sunday4 BOOLEAN NOT NULL, \n"
        + "PRIMARY KEY (timeslotId), \n"
        + "FOREIGN KEY (doctorId) REFERENCES " + TableName.doctor + "(doctorId)"
        + ");";

        String incrementQuery = "ALTER TABLE " + TableName.timeslot + " AUTO_INCREMENT = 1001";

        try {
            Statement statement = connection.createStatement();
            statement.addBatch(createQuery);
            statement.addBatch(incrementQuery);
            statement.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            }
        }
    }
private void createAppointmentTableIfNeeded() {
    if (!doesTableExist(TableName.appointment)) {
        String createQuery = "CREATE TABLE " + TableName.appointment + " (\n"
                + "appointmentId INT NOT NULL AUTO_INCREMENT, \n"
                + "dateCreated DATE NOT NULL, \n"
                + "`amount_to_charge` DECIMAL(10, 2) NULL,"
                + "patientId INT NOT NULL, \n"  // Foreign key reference to the Patient table
                + "timeslotId INT NOT NULL, \n" // Foreign key reference to the Timeslot table
                + "doctorId INT NOT NULL, \n"   // Foreign key reference to the Doctor table
                + "PRIMARY KEY (appointmentId) \n" // Removed the extra comma here
                + ");";

        String incrementQuery = "ALTER TABLE " + TableName.appointment + " AUTO_INCREMENT = 10";

        try {
            Statement statement = connection.createStatement();
            statement.addBatch(createQuery);
            statement.addBatch(incrementQuery);
            statement.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}


    private void createInvoiceTableIfNeed(){
        if (!doesTableExist(TableName.invoice)){
            String createTableSQL =
                    "CREATE TABLE IF NOT EXISTS `" + dbName +"`.`" + TableName.invoice + "` (" +
                            "  `invoiceid` INT NOT NULL AUTO_INCREMENT," +
                            "  `createdat` DATETIME NOT NULL," +
                            "  `updatedat` DATETIME NULL," +
                            "  `gross_total` DECIMAL(10, 2) NULL," +
                            "  `discounted_total` DECIMAL(10, 2) NULL," +
                            "  `total` DECIMAL(10, 2) NULL," +
                            "  `paidat` DATETIME NULL," +
                            "  `paidamount` DECIMAL(10, 2) NULL," +
                            "  `ispaid` TINYINT NULL," +
                            "  `appointment_appointmentid` INT NOT NULL," +
                            "  PRIMARY KEY (`invoiceid`)," +
                            "  INDEX `fk_invoice_appointment1_idx` (`appointment_appointmentid` ASC) VISIBLE," +
                            "  CONSTRAINT `fk_invoice_appointment1`" +
                            "    FOREIGN KEY (`appointment_appointmentid`)" +
                            "    REFERENCES `"+dbName+"`.`"+TableName.appointment+"` (`appointmentId`)" +
                            "    ON DELETE NO ACTION" +
                            "    ON UPDATE NO ACTION)";

            String incrementQuery = "ALTER TABLE " + TableName.invoice + " AUTO_INCREMENT = 1001";

            try {
                Statement statement = connection.createStatement();
                statement.addBatch(createTableSQL);
                statement.addBatch(incrementQuery);
                statement.executeBatch();

                System.out.println("Table '" + TableName.appointment  + "' created successfully in database '" + dbName + "'.");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


    public List<String> getAllPatientNames() {
        List<String> patientNames = new ArrayList<>();

        try {
            String query = "SELECT fName, lName FROM " + TableName.patient;
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String fullName = resultSet.getString("fName") + " " + resultSet.getString("lName");
                patientNames.add(fullName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patientNames;
    }

    public List<String> getAllDoctorNames() {
        List<String> doctorNames = new ArrayList<>();

        try {
            String query = "SELECT name FROM " + TableName.doctor;
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                doctorNames.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorNames;
    }

    
}
