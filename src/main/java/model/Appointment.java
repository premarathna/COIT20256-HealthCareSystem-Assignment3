/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import Utils.DbConnectionManager;
import Utils.TableName;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Appointment {

    public int appointmentId;
    public Date dateCreated;
    public int patientId;
    public int timeslotId;
    public int doctorId;
    public BigDecimal amountToCharge;


    public Appointment( int patientId, Date dateCreated, int timeslotId, int doctorId, int appointmentId,BigDecimal amountToCharge) {
   
        this.dateCreated = dateCreated;
        this.patientId = patientId;
        this.timeslotId = timeslotId;
        this.doctorId = doctorId;
        this.amountToCharge = amountToCharge;
    }

    public Appointment( int patientId, Date dateCreated, int timeslotId, int doctorId, int appointmentId) {

        this.dateCreated = dateCreated;
        this.patientId = patientId;
        this.timeslotId = timeslotId;
        this.doctorId = doctorId;
    }

    public Appointment(int appointmentId, int patientId, BigDecimal amountToCharge) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.amountToCharge = amountToCharge;
    }

    public boolean insertAppointment() {
        String insertQuery = "INSERT INTO " + TableName.appointment + "(\n"
                + "dateCreated, patientId, timeslotId, doctorId, appointmentId,amount_to_charge)\n"
                + "VALUES(?, ?, ?, ?,?,?)";

        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, dateCreated);
            statement.setInt(2, patientId);
            statement.setInt(3, timeslotId);
            statement.setInt(4, doctorId);
             statement.setInt(5, appointmentId);
             statement.setBigDecimal(6, new BigDecimal(1000));
            statement.executeUpdate();
            ResultSet results = statement.getGeneratedKeys();
            System.out.println(appointmentId);
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
                        results.getInt("doctorId"), // This will be the doctor's userId
                        results.getBigDecimal("amount_to_charge")
                );
            }
            return appointment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Appointment findAppointmentById(int appointmentId) {
        System.out.println("find appointment by id = "+appointmentId);
        Appointment appointment = null;
        String query = "SELECT * FROM "+TableName.appointment+" WHERE appointmentId = ?";

        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, appointmentId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                appointment = new Appointment(
                        resultSet.getInt("appointmentId"),
                        resultSet.getInt("patientId"),
                        resultSet.getBigDecimal("amount_to_charge")
                );
            }

        } catch ( SQLException e) {
            e.printStackTrace();
        }
        return appointment;
    }

    public static List<Integer> getAppointmentsWithoutInvoice() {
        String sql = "SELECT appointmentId FROM Appointment " +
                "LEFT JOIN Invoice ON Appointment.appointmentId = Invoice.appointment_appointmentid " +
                "WHERE Invoice.appointment_appointmentid IS NULL";
        List<Integer> appointmentIds = new ArrayList<>();
        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                appointmentIds.add(resultSet.getInt("appointmentId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentIds;
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
