/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Presenter;

import Utils.DbConnectionManager;
import com.mycompany.ginpayroll.App;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author N A A S Senanayake
 */
public class PatientReportViewController implements Initializable {

    @FXML
    private ComboBox<String> cmbMonth;
    @FXML
    private Button btnBackToHome;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnGenerate;
    @FXML
    private ComboBox<Integer> cmbYear;
    PreparedStatement pst;
    DbConnectionManager con = new DbConnectionManager();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbMonth.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        cmbYear.getItems().addAll(2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023);

    }

    @FXML
    private void backToHome(ActionEvent event) {
    }

    @FXML
    private void back(ActionEvent event) throws SQLException {
        try {
            App.setRoot("ReportView");
            pst = con.getConnection().prepareStatement("delete from patient_report");
            pst.executeUpdate();
            System.out.println("Delete successfully");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void generateReport(ActionEvent event) throws JRException {

        try {
            String userEnteredMonth = cmbMonth.getValue();
            Integer userEnteredYear = cmbYear.getValue();

            // Map month names to their corresponding numeric values
            Map<String, Integer> monthToNumeric = new HashMap<>();
            monthToNumeric.put("January", 1);
            monthToNumeric.put("February", 2);
            monthToNumeric.put("March", 3);
            monthToNumeric.put("April", 4);
            monthToNumeric.put("May", 5);
            monthToNumeric.put("June", 6);
            monthToNumeric.put("July", 7);
            monthToNumeric.put("August", 8);
            monthToNumeric.put("September", 9);
            monthToNumeric.put("October", 10);
            monthToNumeric.put("November", 11);
            monthToNumeric.put("December", 12);

            // Check if the user-entered month exists or not
            if (monthToNumeric.containsKey(userEnteredMonth)) {
                int userEnteredMonthNumber = monthToNumeric.get(userEnteredMonth);

                pst = con.getConnection().prepareStatement("SELECT * FROM health_records WHERE MONTH(dateCreated) = ? AND YEAR(dateCreated) = ?");
                pst.setInt(1, userEnteredMonthNumber);
                pst.setInt(2, userEnteredYear);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    pst = con.getConnection().prepareStatement("insert into patient_report(fName,lName,month,disease,age,phone,email,address,dateCreated,insuranceId,patientId )values(?,?,?,?,?,?,?,?,?,?,?)");
                    pst.setString(1, rs.getString("fName"));
                    pst.setString(2, rs.getString("lName"));
                    pst.setString(3, cmbMonth.getValue());
                    pst.setString(4, rs.getString("disease"));
                    pst.setString(5, rs.getString("age"));
                    pst.setString(6, rs.getString("phone"));
                    pst.setString(7, rs.getString("email"));
                    pst.setString(8, rs.getString("address"));
                    pst.setString(9, rs.getString("dateCreated"));
                    pst.setString(10, rs.getString("insuranceId"));
                    pst.setString(11, rs.getString("patientId"));
                    pst.executeUpdate();
                }
                System.out.println("Insert successfully");
            } else {
                System.out.println("Invalid month entered.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\user\\Desktop\\CQU\\COIT20256-HealthCareSystem-Assignment3\\src\\main\\resources\\com\\mycompany\\ginpayroll\\PatientReport.jrxml");
                String query = "select * from patient_report";//
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);
                jdesign.setQuery(updateQuery);
                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, con.getConnection());
                JasperViewer JasperViewer = new JasperViewer(jprint, false);
                JasperViewer.viewReport(jprint, false);
                JasperViewer.dispose();
    }

}
