/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package EmployeeRegistration.Controller;

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
    }

    @FXML
    private void navigateToAppointmentReport(ActionEvent event) {
    }

    @FXML
    private void navigateToFinancialReport(ActionEvent event) {
    }
    
}
