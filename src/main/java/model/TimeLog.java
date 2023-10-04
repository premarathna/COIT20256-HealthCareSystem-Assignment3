/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import Utils.DbConnectionManager;
import Utils.TableName;
import Utils.UserManager;
import java.sql.Date;
import java.sql.Statement;

/**
 *
 * @author Chamali
 */
public class TimeLog {
    private Date date;
    private double hours;
    private int employeeId;
    
    public TimeLog(Date date, double hours) {
        this.date = date;
        this.hours = hours;
        this.employeeId = UserManager.shared().getUser().getEmployeeId();
    }
    
    public boolean insertTimeLog() {
        String insertQuery = "INSERT INTO " + TableName.timeLog + "\n"
        +"(date, hours, employeeId)\n"
        +"VALUES('" + this.date + "', \n"
        +"'" + this.hours + "', \n"  
        +"'" + this.employeeId + "');";
        
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
