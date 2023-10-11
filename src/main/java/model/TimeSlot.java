/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import Utils.DbConnectionManager;
import Utils.TableName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TimeSlot {
    private int doctorId;
    private boolean monday1;
    private boolean monday2;
    private boolean monday3;
    private boolean monday4;
    private boolean tuesday1;
    private boolean tuesday2;
    private boolean tuesday3;
    private boolean tuesday4;
    private boolean wednesday1;
    private boolean wednesday2;
    private boolean wednesday3;
    private boolean wednesday4;
    private boolean thursday1;
    private boolean thursday2;
    private boolean thursday3;
    private boolean thursday4;
    private boolean friday1;
    private boolean friday2;
    private boolean friday3;
    private boolean friday4;
    private boolean saturday1;
    private boolean saturday2;
    private boolean saturday3;
    private boolean saturday4;
    private boolean sunday1;
    private boolean sunday2;
    private boolean sunday3;
    private boolean sunday4;

    public TimeSlot(int doctorId, boolean defaultValue) {
        this.doctorId = doctorId;
        this.monday1 = defaultValue;
        this.monday2 = defaultValue;
        this.monday3 = defaultValue;
        this.monday4 = defaultValue;
        this.tuesday1 = defaultValue;
        this.tuesday2 = defaultValue;
        this.tuesday3 = defaultValue;
        this.tuesday4 = defaultValue;
        this.wednesday1 = defaultValue;
        this.wednesday2 = defaultValue;
        this.wednesday3 = defaultValue;
        this.wednesday4 = defaultValue;
        this.thursday1 = defaultValue;
        this.thursday2 = defaultValue;
        this.thursday3 = defaultValue;
        this.thursday4 = defaultValue;
        this.friday1 = defaultValue;
        this.friday2 = defaultValue;
        this.friday3 = defaultValue;
        this.friday4 = defaultValue;
        this.saturday1 = defaultValue;
        this.saturday2 = defaultValue;
        this.saturday3 = defaultValue;
        this.saturday4 = defaultValue;
        this.sunday1 = defaultValue;
        this.sunday2 = defaultValue;
        this.sunday3 = defaultValue;
        this.sunday4 = defaultValue;
    }

    public TimeSlot(int doctorId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean insertTimeSlot() {
        String insertQuery = "INSERT INTO " + TableName.timeslot + " (\n"
                + "doctorId, monday1, monday2, monday3, monday4, tuesday1, tuesday2, tuesday3, tuesday4, \n"
                + "wednesday1, wednesday2, wednesday3, wednesday4, thursday1, thursday2, thursday3, thursday4, \n"
                + "friday1, friday2, friday3, friday4, saturday1, saturday2, saturday3, saturday4, \n"
                + "sunday1, sunday2, sunday3, sunday4) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbConnectionManager.shared().getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setInt(1, doctorId);
            statement.setBoolean(2, monday1);
            statement.setBoolean(3, monday2);
            statement.setBoolean(4, monday3);
            statement.setBoolean(5, monday4);
            statement.setBoolean(6, tuesday1);
            statement.setBoolean(7, tuesday2);
            statement.setBoolean(8, tuesday3);
            statement.setBoolean(9, tuesday4);
            statement.setBoolean(10, wednesday1);
            statement.setBoolean(11, wednesday2);
            statement.setBoolean(12, wednesday3);
            statement.setBoolean(13, wednesday4);
            statement.setBoolean(14, thursday1);
            statement.setBoolean(15, thursday2);
            statement.setBoolean(16, thursday3);
            statement.setBoolean(17, thursday4);
            statement.setBoolean(18, friday1);
            statement.setBoolean(19, friday2);
            statement.setBoolean(20, friday3);
            statement.setBoolean(21, friday4);
            statement.setBoolean(22, saturday1);
            statement.setBoolean(23, saturday2);
            statement.setBoolean(24, saturday3);
            statement.setBoolean(25, saturday4);
            statement.setBoolean(26, sunday1);
            statement.setBoolean(27, sunday2);
            statement.setBoolean(28, sunday3);
            statement.setBoolean(29, sunday4);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
