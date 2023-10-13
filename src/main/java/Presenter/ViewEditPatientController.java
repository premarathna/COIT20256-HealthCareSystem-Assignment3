/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Presenter;

import Utils.Helper;
import com.mycompany.ginpayroll.App;
import java.io.IOException;
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
import javafx.scene.input.KeyEvent;
import model.Patient;
import model.User;
/**
 * FXML Controller class
 *
 * @author Chamali
 */
public class ViewEditPatientController implements Initializable {


    @FXML
    private DatePicker dobPicker;
    @FXML
    private TextArea addressField;
    @FXML
    private TextField mobileField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField genderField;
    @FXML
    private Button updateButton;
    @FXML
    private TextField searchField;
    @FXML
    private TextField fnameField;
    @FXML
    private TextField insuranceIdField;
    @FXML
    private TextField patientIDField;
    @FXML
    private TextField lnameField;
    
    private Patient patient;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onBackTapped(ActionEvent event) {
        try {
                App.setRoot("AdminView");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }

    @FXML
    private void onClickUpdate(ActionEvent event) {
        this.patient.fName = fnameField.getText();
        this.patient.lName = lnameField.getText();
        this.patient.address = addressField.getText();
        this.patient.dob = Date.valueOf(dobPicker.getValue());
        this.patient.mobile = mobileField.getText();
        this.patient.email = emailField.getText();
        this.patient.insuaranceId = insuranceIdField.getText();
       
        if (validateUserInput()) {
            Patient patientModel = getPatientModel();
            boolean success = patientModel.updatePatient();
            System.out.print("=============");
        if (success) {
            Helper.showAlert("Success", "Patient updated succesfully!");
        } else {
           Helper.showAlert("Failure", "Patient upadte failed. Try Again!");
        }
        }
    }

    @FXML
    private void onTextChanged(KeyEvent event) {
        String text = searchField.getText();
        // Search only if input is not empty AND
        // is a number AND number > 1000 (Since patient IDs start from 1000)
        if(!text.isEmpty() 
                && Helper.onlyContainsNumbers(text)
                && Integer.parseInt(text) > 1000) {
            
            Patient patient = Patient.fetchPatientBy(Integer.parseInt(text));
            if (patient != null) {
                // Employee Found
                this.patient = patient;
                populateFields();
            } else {
                resetFields();
            }
        } else {
            resetFields();
        }
    }
    
    private void populateFields() {
        patientIDField.setText(Integer.toString(patient.patientId));
        fnameField.setText(patient.fName);
        lnameField.setText(patient.lName);
        dobPicker.setValue(patient.dob.toLocalDate());
        addressField.setText(patient.address);
        genderField.setText(patient.gender);
        mobileField.setText(patient.mobile);
        emailField.setText(patient.email);
        insuranceIdField.setText(patient.insuaranceId);
    }
    
      private void resetFields() {
        patientIDField.setText(null);
        emailField.setText(null);
        fnameField.setText(null);
        dobPicker.setValue(null);
        addressField.setText(null);
        mobileField.setText(null);
        emailField.setText(null);
        insuranceIdField.setText(null);
        lnameField.setText(null);
        genderField.setText(null);
    }
      
     private boolean validateUserInput() {
        if(allFieldsHaveValues()) {
            // Email Validation
            if(Helper.isEmailValid(emailField.getText())) {
                // Mobile number validation
                if(Helper.isMobileValid(mobileField.getText())) {
                    //Name Validation
                    if(Helper.islNameValid(lnameField.getText())) {
                        // Account number valdation
                        if(Helper.isfNameValid(fnameField.getText())) {
                            // Password Validation
                            if(Helper.isInsuaranceIdValid(insuranceIdField.getText())) {
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
        if(mobileField.getText().isBlank()) {
            isValid = false;
        }
        if(genderField.getText().isBlank()) {
            isValid = false;
        }

        if(emailField.getText().isBlank()) {
            isValid = false;
        }
        if(insuranceIdField.getText().isBlank()) {
            isValid = false;
        }
        if(dobPicker.getValue() == null) {
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
            mobileField.getText(), 
            emailField.getText(), 
            Date.valueOf(dobPicker.getValue()),
            insuranceIdField.getText()
        );
    }

}
