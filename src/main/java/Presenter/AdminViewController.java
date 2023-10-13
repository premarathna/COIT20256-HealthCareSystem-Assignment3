/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Presenter;

import com.mycompany.ginpayroll.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
/**
 * FXML Controller class
 *
 * @author Chamali
 */
public class AdminViewController implements Initializable {


    @FXML
    private Button vieweportsBtn;
    @FXML
    private Button regPatientBtn;
    @FXML
    private Button viewEditPatientBtn;
    @FXML
    private Button makeAppoinmentBtn;
    @FXML
    private Button billingBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onClickRegisterPatient(ActionEvent event) {
        try {
            App.setRoot("AddPatient");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onClickReports(ActionEvent event) {
         try {
            App.setRoot("ReportView");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onClickEditPatient(ActionEvent event) {
         try{
                App.setRoot("ViewEditPatient");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }

    @FXML
    private void onClickMakeAppoinment(ActionEvent event) {
        try {
            // Attempt to navigate to the "CreateAppointment" view
            App.setRoot("CreateAppointment");
        } catch (IOException ex) {
            // Handle any IOException that may occur during the navigation
            System.out.println("Error while navigating to CreateAppointment: " + ex.getMessage());
        }
    }

    @FXML
    private void onClickBilling(ActionEvent event) {
    }
    
}
