/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import Utils.DbConnectionManager;
import Utils.TableName;
import Utils.UserManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Chamali
 */
public class PaySlip {
    public String name;
    public double hourlyRate;
    public double taxRate;
    public double superRate;
    public double totalHours;
    public Date startDate;
    public Date endDate;
    
    public PaySlip (Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public boolean fetchAndUpdatePalySlip() {    
        int empId = UserManager.shared().getUser().getEmployeeId();
        String query = "SELECT e.name AS name,  e.taxRate, e.hourlyRate, e.superRate, SUM(t.hours) AS totalHours\n" 
                + "FROM " + TableName.employee + " e, " + TableName.timeLog + " t\n"
                + "WHERE e.employeeId = t.employeeId\n"
                + "AND e.employeeId = " + empId + "\n"
                + "AND t.date BETWEEN '" + this.startDate + "' AND '" + this.endDate + "'\n"
                + "GROUP BY name";

        try {
            Connection connection =  DbConnectionManager.shared().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();
            
            if (results.next()) {
                this.name = results.getString("name");
                this.hourlyRate = results.getDouble("hourlyRate");
                this.taxRate = results.getDouble("taxRate");
                this.superRate = results.getDouble("superRate");
                this.totalHours = results.getDouble("totalHours");
            }       
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
