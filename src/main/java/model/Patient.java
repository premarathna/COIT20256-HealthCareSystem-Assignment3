/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import Utils.DbConnectionManager;
import Utils.TableName;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
    
    public boolean insertPatient() {
        String insertQuery = "INSERT INTO " + TableName.patient + "(\n"
            +"fName, lNAme, address, gender, mobile, email, insuranceId, dob)\n"
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
    
    
}
