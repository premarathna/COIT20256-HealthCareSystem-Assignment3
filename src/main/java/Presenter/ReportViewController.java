/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Presenter;

import com.mycompany.ginpayroll.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author N A A S Senanayake
 */
public class ReportViewController implements Initializable {

    @FXML
    private Button btnPatientReport;
    @FXML
    private Button btnAppointmentReport;
    @FXML
    private Button btnFinancialReport;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void navigateToPatientReport(ActionEvent event) {
        try {
            App.setRoot("PatientReportView");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void navigateToAppointmentReport(ActionEvent event) {
        try {
            App.setRoot("AppointmentReportView");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void navigateToFinancialReport(ActionEvent event) {
        try {
            App.setRoot("FinancialReportView");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
