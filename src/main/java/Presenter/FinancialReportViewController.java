/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Presenter;

import Utils.DbConnectionManager;
import com.mycompany.ginpayroll.App;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
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
public class FinancialReportViewController implements Initializable {

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
    private void back(ActionEvent event) {
        try {
            App.setRoot("ReportView");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void generateReport(ActionEvent event) throws JRException {
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

                pst = con.getConnection().prepareStatement("SELECT * FROM invoice WHERE MONTH(date) = ? AND YEAR(date) = ?");
                pst.setInt(1, userEnteredMonthNumber);
                pst.setInt(2, userEnteredYear);
                ResultSet rs1 = pst.executeQuery();
                System.out.println("Get details from invoice");

                // Check if rs1 is empty or not before entering the while loop
                if (!rs1.next()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Health Care System");
                    alert.setContentText("No invoice records found for this Year and Month.");
                    alert.showAndWait();

                    System.out.println("No invoice records found");
                } else {
                    // Start the while loop
                    do {
                        pst = con.getConnection().prepareStatement("SELECT patientId,doctorId FROM appoinment WHERE appoinmentId = ?");
                        pst.setInt(1, rs1.getInt("appoinmentId"));
                        ResultSet rs2 = pst.executeQuery();
                        System.out.println("Get details from appoinment");

                        if (rs2.next()) {
                            pst = con.getConnection().prepareStatement("SELECT fName,lName FROM patient WHERE patientId = ?");
                            pst.setInt(1, rs2.getInt("patientId"));
                            ResultSet rs3 = pst.executeQuery();
                            System.out.println("Get details from patient");

                            pst = con.getConnection().prepareStatement("SELECT name FROM doctor WHERE doctorId = ?");
                            pst.setInt(1, rs2.getInt("doctorId"));
                            ResultSet rs4 = pst.executeQuery();
                            System.out.println("Get details from doctor");

                            if (rs3.next() & rs4.next()) {
                                pst = con.getConnection().prepareStatement("insert into invoice_report(invoiceId,date,time,dateCreated,appoinmentId,doctorName,patientFName,patientLName,payment,grossTotal,netTotal,discountedTotal)values(?,?,?,?,?,?,?,?,?,?,?,?)");
                                pst.setInt(1, rs1.getInt("invoiceId"));
                                pst.setString(2, rs1.getString("date"));
                                pst.setString(3, rs1.getString("time"));
                                pst.setString(4, rs1.getString("dateCreated"));
                                pst.setInt(5, rs1.getInt("appoinmentId"));
                                pst.setString(6, rs4.getString("name"));
                                pst.setString(7, rs3.getString("fName"));
                                pst.setString(8, rs3.getString("lName"));
                                pst.setFloat(9, rs1.getFloat("payment"));
                                pst.setString(10, rs1.getString("grossTotal"));
                                pst.setString(11, rs1.getString("netTotal"));
                                pst.setString(12, rs1.getString("discountedTotal"));
                                pst.executeUpdate();
                            }
                        }
                    } while (rs1.next());

                    System.out.println("Insert successfully to invoice_report");

                    JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\user\\Desktop\\CQU\\COIT20256-HealthCareSystem-Assignment3\\src\\main\\resources\\com\\mycompany\\ginpayroll\\FinancialReport.jrxml");
                    String query = "select * from invoice_report";//
                    JRDesignQuery updateQuery = new JRDesignQuery();
                    updateQuery.setText(query);
                    jdesign.setQuery(updateQuery);
                    JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                    JasperPrint jprint = JasperFillManager.fillReport(jreport, null, con.getConnection());
                    JasperViewer JasperViewer = new JasperViewer(jprint, false);
                    JasperViewer.viewReport(jprint, false);
                    JasperViewer.dispose();

                    pst = con.getConnection().prepareStatement("delete from invoice_report");
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
