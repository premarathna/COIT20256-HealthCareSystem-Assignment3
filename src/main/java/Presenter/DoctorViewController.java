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
 * @author Chamali
 */
public class DoctorViewController implements Initializable {


    @FXML
    private Button viewPatientBtn;
    @FXML
    private Button medicalHistoryBtn;
    @FXML
    private Button changeAvailabilityBtn;
    @FXML
    private int userId; // Add a field to store the userId
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onClickViewPatient(ActionEvent event) {
        try{
                App.setRoot("ViewEditPatient");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }

    @FXML
    private void onViewMedicalHistoryTapped(ActionEvent event) {
    }
    @FXML
    private void onChangeAvailability(ActionEvent event) {
                try{
                App.setRoot("ChangeAvailability");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
