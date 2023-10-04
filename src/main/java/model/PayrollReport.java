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

/**
 *
 * @author Chamali
 */
public class PayrollReport {
    public double totalNetPay = 0;
    public double totalTaxPay = 0;
    public double totalSuperPay = 0;
    public Date startDate;
    public Date endDate;
    
    public PayrollReport (Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public boolean fetchAndUpdatePayrollReport() {    
        String query = "SELECT e.hourlyRate * SUM(t.hours) AS totalPay,\n" 
                + "(e.hourlyRate * SUM(t.hours) / 100 ) * e.taxRate AS totalTax,\n"
                + "(e.hourlyRate * SUM(t.hours) / 100 ) * e.superRate AS totalSuper\n"
                + "FROM employee e, TimeLog t\n"
                + "WHERE e.employeeId = t.employeeId\n"
                + "AND t.date BETWEEN '" + this.startDate + "' AND '" + this.endDate + "'\n"
                + "GROUP BY e.employeeId";

        try {
            Connection connection =  DbConnectionManager.shared().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();
            
            while(results.next()) {
                double totalPay = results.getDouble("totalPay");
                double taxPay = results.getDouble("totalTax");
                
                this.totalTaxPay += taxPay;
                this.totalSuperPay += results.getDouble("totalSuper");
                this.totalNetPay += (totalPay - taxPay);
            }       
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
