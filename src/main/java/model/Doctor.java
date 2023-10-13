/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import Utils.DbConnectionManager;
import Utils.TableName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Doctor {
    private int doctorId;  // Unique identifier for the doctor
    private int userId;    // Foreign key to link with User table
    private String name;   // Combined name of the doctor
    private String availability;  // String for availability

    public Doctor(int doctorId, int userId, String name, String availability) {
        this.doctorId = doctorId;
        this.userId = userId;
        this.name = name;
        this.availability = availability;
    }
   public int getDoctorId() {
        return this.doctorId;
    }
    public boolean insertDoctor() {
        String insertQuery = "INSERT INTO " + TableName.doctor + "(\n"
                + " doctorId, userId, name, availability)\n"
                + "VALUES(?, ?, ?, ?)";

        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, doctorId);
            statement.setInt(2, userId);
            statement.setString(3, name);
            statement.setString(4, availability);
            statement.executeUpdate();
            ResultSet results = statement.getGeneratedKeys();

            return results.next();  // Return true on success, false on failure.
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Doctor fetchDoctorByUserId(int userId) {
        String selectQuery = "SELECT * FROM " + TableName.doctor + " WHERE userId = ?";

        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setInt(1, userId);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                int doctorId = results.getInt("doctorId");
                String name = results.getString("name");
                String availability = results.getString("availability");
                return new Doctor( doctorId, userId, name, availability);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Return a Doctor object on success, or null on failure.
    }

}
