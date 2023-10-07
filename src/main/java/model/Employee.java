///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package model;
//import Utils.DbConnectionManager;
//import Utils.TableName;
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
//
///**
// *
// * @author Chamali
// */
//public class Employee {
//    public int employeeId;
//    public String name;
//    public String address;
//    public String mobile;
//    public String email;
//    public Date dob;
//    public String designation;
//    public double hourlyRate;
//    public double taxRate;
//    public double superRate;
//    public String accountNumber;
//    public User user;
//    
//    public Employee(int employeeId, String name, String address, 
//            String mobile, String email, String accountNumber,
//            String designation, double hourlyRate, double taxRate, 
//            double superRate, Date dob, User user)  {
//    
//        this.employeeId = employeeId;
//        this.name = name;
//        this.address = address;
//        this.mobile = mobile;
//        this.email = email;
//        this.accountNumber = accountNumber;
//        this.designation = designation;
//        this.hourlyRate = hourlyRate;
//        this.taxRate = taxRate;
//        this.superRate = superRate;
//        this.dob = dob;
//        this.user = user;
//    }
//    
//    public boolean insertEmployee() {
//        String insertQuery = "INSERT INTO " + TableName.employee + "(\n"
//            +"name, address, mobile, email, accountNumber, designation, hourlyRate, taxRate, superRate, dob)\n"
//            +"VALUES('" + this.name + "', \n"
//            +"'" + this.address + "', \n"
//            +"'" + this.mobile + "', \n"
//            +"'" + this.email + "', \n"
//            +"'" + this.accountNumber + "', \n"
//            +"'" + this.designation + "', \n"
//            +"'" + this.hourlyRate + "', \n"
//            +"'" + this.taxRate + "', \n"
//            +"'" + this.superRate + "', \n"
//            +"'" + this.dob + "');";
//        
//        try {
//            Connection connection =  DbConnectionManager.shared().getConnection();
//            PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
//            statement.executeUpdate();
//            ResultSet results = statement.getGeneratedKeys();
//            
//            // Adding Emloyee Login details to Login table
//            if (results.next()) {
//                int employeeId = results.getInt(1);
//                System.out.println(employeeId);
//                User user = this.user;
//                user.setEmployeeId(employeeId);
//                user.insertUser(); 
//            }            
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    
//    public boolean updateUser() {
//        String updateQuery = "UPDATE " + TableName.employee + "\n"
//            +"SET \n"
//            +"name = '" + this.name + "', \n"
//            +"address = '" + this.address + "', \n"
//            +"mobile = '" + this.mobile + "', \n"
//            +"email = '" + this.email + "', \n"
//            +"accountNumber = '" + this.accountNumber + "', \n"
//            +"designation = '" + this.designation + "', \n"
//            +"hourlyRate = '" + this.hourlyRate + "', \n"
//            +"taxRate = '" + this.taxRate + "', \n"
//            +"superRate = '" + this.superRate + "', \n"
//            +"dob = '" + this.dob + "' \n"
//            +"WHERE employeeId = '" + this.employeeId + "';";
//        
//        try {
//            Statement statement = DbConnectionManager.shared().getConnection().createStatement();
//            statement.execute(updateQuery);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    
//    public static Employee fetchUserBy(int employeeId) {
//        String selectQuery = String.format(
//            "SELECT * FROM %s e, %s l WHERE e.employeeId = l.employeeId AND e.employeeId = %s LIMIT 1", 
//            TableName.employee, TableName.login, employeeId);
//        try {
//            Statement statement = DbConnectionManager.shared().getConnection().createStatement();
//            ResultSet results = statement.executeQuery(selectQuery);
//       
//            Employee employee = null;
//            while (results.next()) {
//                User user = new User(
//                        results.getString("username"), 
//                        results.getString("password"), 
//                        results.getString("role"), 
//                        results.getInt("employeeId")
//                );
//                employee = new Employee(
//                        results.getInt("employeeId"),
//                        results.getString("name"), 
//                        results.getString("address"), 
//                        results.getString("mobile"),  
//                        results.getString("email"), 
//                        results.getString("accountNumber"), 
//                        results.getString("designation"), 
//                        results.getInt("hourlyRate"), 
//                        results.getInt("taxRate"), 
//                        results.getInt("superRate"),
//                        results.getDate("dob"),
//                        user
//                );
//            }
//            return employee;
//            // TODO: Query Login table to get username and password
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
