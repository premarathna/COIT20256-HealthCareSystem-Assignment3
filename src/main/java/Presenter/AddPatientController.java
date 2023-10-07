/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Presenter;

import Utils.Constants;
import Utils.Helper;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Patient;
/**
 * FXML Controller class
 *
 * @author Chamali
 */
public class AddPatientController implements Initializable {


    @FXML
    private TextField fnameField;
    @FXML
    private DatePicker dobField;
    @FXML
    private TextArea addressField;
    @FXML
    private TextField mobileNoField;
    @FXML
    private TextField genderField;
    @FXML
    private Button backBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private TextField emailField;
    @FXML
    private TextField insuaranceIdField;
    @FXML
    private TextField lnameField;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onClickBack(ActionEvent event) {
    }

    @FXML
    private void onClickSave(ActionEvent event) {
         if (validateUserInput()) {
            Patient patientModel = getPatientModel();
            boolean success = patientModel.insertPatient();
            if(success) {
                Helper.showAlert("Success", "Patient added succesfully!");
                clearUserInput();
            } else {
                Helper.showAlert("Error", "Failed to add Patient");
            }
        } else {
            System.out.println("Validation Failed");
        }
    }
    
    private boolean validateUserInput() {
        if(allFieldsHaveValues()) {
            // Email Validation
            if(Helper.isEmailValid(emailField.getText())) {
                // Mobile number validation
                if(Helper.isMobileValid(mobileNoField.getText())) {
                    //Name Validation
                    if(Helper.islNameValid(lnameField.getText())) {
                        // Account number valdation
                        if(Helper.isfNameValid(fnameField.getText())) {
                            // Password Validation
                            if(Helper.isInsuaranceIdValid(insuaranceIdField.getText())) {
                                return true;
                            } else {
                                Helper.showAlert("Invalid", "Insuarnace number Invaid, enter valida Insuarnace number");
                                return false;
                            }
                        } else {
                            Helper.showAlert("Invalid", "FirstName can only include letters");
                            return false;
                        }
                    } else {
                         Helper.showAlert("Invalid", " LastName can only include letters");
                         return false;
                    }
                } else {
                    Helper.showAlert("Invalid", "Invalid mobile number");
                    return false;
                }
            } else {
                Helper.showAlert("Invalid", "Invalid email address");
                return false;
            }
        } else {
            Helper.showAlert("Invalid", "Fileds cannot be empty");
            return false;
        }
    }
    
     private void clearUserInput() {
        fnameField.setText(null);
        lnameField.setText(null);
        dobField.setValue(null);
        addressField.setText(null);
        mobileNoField.setText(null);
        genderField.setText(null);
        emailField.setText(null);
        insuaranceIdField.setText(null);
        
    }
    
    private boolean allFieldsHaveValues() {
        boolean isValid = true;
        
        if(fnameField.getText().isBlank()) {
            isValid = false;
        }
         if(lnameField.getText().isBlank()) {
            isValid = false;
        }
        if(addressField.getText().isBlank()) {
            isValid = false;
        }
        if(mobileNoField.getText().isBlank()) {
            isValid = false;
        }
        if(genderField.getText().isBlank()) {
            isValid = false;
        }

        if(emailField.getText().isBlank()) {
            isValid = false;
        }
        if(insuaranceIdField.getText().isBlank()) {
            isValid = false;
        }
        if(dobField.getValue() == null) {
            isValid = false;
        }
        return isValid;
    }
    
        
    private Patient getPatientModel() {
        
        return new Patient(
            0,
            fnameField.getText(), 
            lnameField.getText(), 
            addressField.getText(), 
            genderField.getText(),
            mobileNoField.getText(), 
            emailField.getText(),  
            Date.valueOf(dobField.getValue()),
            insuaranceIdField.getText()
        );
    }
    

}
