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
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;

public class Appointment {

    public int appointmentId;
    public Date dateCreated;
    public int patientId;
    public int timeslotId;
    public int doctorId;

    public Appointment( int patientId, Date dateCreated, int timeslotId, int doctorId, int appointmentId) {
   
        this.dateCreated = dateCreated;
        this.patientId = patientId;
        this.timeslotId = timeslotId;
        this.doctorId = doctorId;
    }

    public boolean insertAppointment() {
        String insertQuery = "INSERT INTO " + TableName.appointment + "(\n"
                + "dateCreated, patientId, timeslotId, doctorId, appointmentId)\n"
                + "VALUES(?, ?, ?, ?,?)";

        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, dateCreated);
            statement.setInt(2, patientId);
            statement.setInt(3, timeslotId);
            statement.setInt(4, doctorId);
             statement.setInt(5, appointmentId);
            statement.executeUpdate();
            ResultSet results = statement.getGeneratedKeys();
            
            // You can handle the results and return true if the insertion was successful.
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
     
        }
    }

    public static Appointment fetchAppointmentBy(int appointmentId) {
        String selectQuery = String.format(
                "SELECT a.*, u.userId AS doctorId " +
                "FROM %s a " +
                "INNER JOIN %s u ON a.doctorId = u.userId " +
                "WHERE a.appointmentId = %s " +
                "LIMIT 1",
                TableName.appointment, TableName.user, appointmentId);
        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(selectQuery);

            Appointment appointment = null;
            while (results.next()) {
                appointment = new Appointment(
                        results.getInt("appointmentId"),
                        results.getDate("dateCreated"),
                        results.getInt("patientId"),
                        results.getInt("timeslotId"),
                        results.getInt("doctorId") // This will be the doctor's userId
                );
            }
            return appointment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean updateAppointment() {
        String updateQuery = "UPDATE " + TableName.appointment + "\n"
                + "SET \n"
                + "dateCreated = ?, \n"
                + "patientId = ?, \n"
                + "timeslotId = ?, \n"
                + "doctorId = ? \n"
                + "WHERE appointmentId = ?";

        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setDate(1, dateCreated);
            statement.setInt(2, patientId);
            statement.setInt(3, timeslotId);
            statement.setInt(4, doctorId);
            statement.setInt(5, appointmentId);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
