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
import javafx.scene.control.Alert;

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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void generateReport(ActionEvent event) throws JRException, SQLException {

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
                ResultSet rs1 = pst.executeQuery();
                System.out.println("Get details from health_records");

                // Check if rs1 is empty or not before entering the while loop
                if (!rs1.next()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Health Care System");
                    alert.setContentText("No patient records found for this Year and Month.");
                    alert.showAndWait();

                    System.out.println("No patient records found");
                } else {
                    // Start the while loop
                    do {
                        pst = con.getConnection().prepareStatement("SELECT * FROM patient WHERE patientId = ?");
                        pst.setInt(1, rs1.getInt("patientId"));
                        ResultSet rs2 = pst.executeQuery();
                        System.out.println("Get details from patient");

                        if (rs2.next()) {
                            pst = con.getConnection().prepareStatement("insert into patient_report(fName,lName,phone,email,address,dateCreated,insuranceId,patientId )values(?,?,?,?,?,?,?,?)");
                            pst.setString(1, rs2.getString("fName"));
                            pst.setString(2, rs2.getString("lName"));
                            pst.setString(3, rs2.getString("mobile"));
                            pst.setString(4, rs2.getString("email"));
                            pst.setString(5, rs2.getString("address"));
                            pst.setString(6, rs1.getString("dateCreated"));
                            pst.setString(7, rs2.getString("insuranceId"));
                            pst.setInt(8, rs2.getInt("patientId"));
                            pst.executeUpdate();
                        }
                    } while (rs1.next());

                    System.out.println("Insert successfully to patient_report");

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

                    pst = con.getConnection().prepareStatement("delete from patient_report");
                    pst.executeUpdate();
                    System.out.println("Delete successfully");
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Health Care System");
                alert.setContentText("Invalid Year and Month.");
                alert.showAndWait();

                System.out.println("Invalid month entered.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
