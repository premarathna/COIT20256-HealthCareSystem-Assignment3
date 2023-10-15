/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import Utils.DbConnectionManager;
import Utils.TableName;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;

/**
 *
 * @author Chamali
 */
public class Patient {
    
    public int patientId;
    public String fName;
    public String lName;
    public String address;
    public String gender;
    public String mobile;
    public String email;
    public Date dob;
    public String insuaranceId;
    
    public Patient(int patientId, String fName, String lName, 
            String address, String gender, String mobile, 
            String email, Date dob, String insuranceId) {
        
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.gender = gender;
        this.mobile = mobile;
        this.email = email;
        this.dob = dob;
        this.insuaranceId = insuranceId;
    
    }


    public String getAvailableBName() {
        if (StringUtils.isNotBlank(fName) && StringUtils.isNotBlank(lName)) {
            return fName + " " + lName;
        } else if (StringUtils.isNotBlank(fName)) {
            return fName;
        } else {
            return lName;
        }
    }
    
    public boolean insertPatient() {
        String insertQuery = "INSERT INTO " + TableName.patient + "(\n"
            +"fName, lNAme, address, gender, mobile, email, insuaranceId, dob)\n"
            +"VALUES('" + this.fName + "', \n"
            +"'" + this.lName + "', \n"
            +"'" + this.address + "', \n"
            +"'" + this.gender + "', \n"
            +"'" + this.mobile + "', \n"
            +"'" + this.email + "', \n"
            +"'" + this.insuaranceId + "', \n"
            +"'" + this.dob + "');";
        
        try {
            Connection connection =  DbConnectionManager.shared().getConnection();
            PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet results = statement.getGeneratedKeys();
            
            // Adding Emloyee Login details to Login table          
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static Patient fetchPatientBy(int patientId) {
        String selectQuery = String.format(
            "SELECT * FROM %s p WHERE p.patientId = %s LIMIT 1", 
            TableName.patient, patientId);
        try {
            Statement statement = DbConnectionManager.shared().getConnection().createStatement();
            ResultSet results = statement.executeQuery(selectQuery);
       
            Patient patient = null;
            while (results.next()) {
                patient = new Patient(
                        results.getInt("patientId"),
                        results.getString("fName"), 
                        results.getString("lName"), 
                        results.getString("address"),
                        results.getString("gender"), 
                        results.getString("mobile"),  
                        results.getString("email"), 
                        results.getDate("dob"),
                        results.getString("insuaranceId")
                );
            }
            return patient;
            // TODO: Query Login table to get username and password
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Patient findPatientById(int patientId) {
        System.out.println("find patient by id "+patientId);
        Patient patient = null;
        String query = "SELECT * FROM "+TableName.patient+" WHERE patientId = ?";
        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, patientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int patientid = resultSet.getInt("patientId");
                String fName = resultSet.getString("fName");
                String lName = resultSet.getString("lName");
                String address = resultSet.getString("address");
                String mobile = resultSet.getString("mobile");
                String email = resultSet.getString("email");
                String gender = resultSet.getString("gender");
                String insuranceID = resultSet.getString("insuaranceID");
                Date dob = resultSet.getDate("dob");

                patient = new Patient(patientid,fName,lName,address,gender,mobile,email,dob,insuranceID);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }
    
    public boolean updatePatient() {
        String updateQuery = "UPDATE " + TableName.patient + "\n"
            +"SET \n"
            +"fName = '" + this.fName + "', \n"
            +"lName = '" + this.lName + "', \n"
            +"address = '" + this.address + "', \n"
            +"gender = '" + this.gender + "', \n"
            +"mobile = '" + this.mobile + "', \n"
            +"email = '" + this.email + "', \n"
            +"dob = '" + this.dob + "', \n"
            +"insuaranceId = '" + this.insuaranceId + "' \n"
            +"WHERE patientId = '" + this.patientId + "';";
        
        try {
            Statement statement = DbConnectionManager.shared().getConnection().createStatement();
            statement.execute(updateQuery);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
}
