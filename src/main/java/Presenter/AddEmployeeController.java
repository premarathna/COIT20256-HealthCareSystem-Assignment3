/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Presenter;

import Utils.Constants;
import Utils.Helper;
import com.mycompany.ginpayroll.App;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Employee;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import model.User;

/**
 * FXML Controller class
 *
 * @author Chamali
 */
public class AddEmployeeController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private DatePicker dobField;
    @FXML
    private TextArea addressField;
    @FXML
    private TextField mobileNoField;
    @FXML
    private TextField hourlyRateField;
    @FXML
    private TextField taxRateField;
    @FXML
    private Button backBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField bankAccField;
    @FXML
    private TextField designationField;
    @FXML
    private TextField superRateField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void onClickSave(ActionEvent event) {
        if (validateUserInput()) {
            Employee employeeModel = getEmployeeModel();
            boolean success = employeeModel.insertEmployee();
            if(success) {
                Helper.showAlert("Success", "Employee added succesfully!");
                clearUserInput();
            } else {
                Helper.showAlert("Error", "Failed to add employee");
            }
        } else {
            System.out.println("Validation Failed");
        }
    }
    
    private void clearUserInput() {
        nameField.setText(null);
        dobField.setValue(null);
        addressField.setText(null);
        mobileNoField.setText(null);
        hourlyRateField.setText(null);
        taxRateField.setText(null);
        usernameField.setText(null);
        emailField.setText(null);
        passwordField.setText(null);
        bankAccField.setText(null);
        designationField.setText(null);
        superRateField.setText(null);
        
    }
    
    private boolean validateUserInput() {
        if(allFieldsHaveValues()) {
            // Email Validation
            if(Helper.isEmailValid(emailField.getText())) {
                // Mobile number validation
                if(Helper.isMobileValid(mobileNoField.getText())) {
                    //Name Validation
                    if(Helper.isNameValid(nameField.getText())) {
                        // Account number valdation
                        if(Helper.isAccountNumberValid(bankAccField.getText())) {
                            // Password Validation
                            if(Helper.isPasswordValid(passwordField.getText())) {
                                return true;
                            } else {
                                Helper.showAlert("Invalid", "Password Invaid, enter valida password");
                                return false;
                            }
                        } else {
                            Helper.showAlert("Invalid", "Account number can only have numbers");
                            return false;
                        }
                    } else {
                         Helper.showAlert("Invalid", "Name can only include letters");
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
    
    private Employee getEmployeeModel() {
        User user = new User(
                usernameField.getText(), 
                passwordField.getText(),
                Constants.employeeRole
        );
        
        return new Employee(
            0,
            nameField.getText(), 
            addressField.getText(), 
            mobileNoField.getText(), 
            emailField.getText(), 
            bankAccField.getText(), 
            designationField.getText(), 
            Double.valueOf(hourlyRateField.getText()), 
            Double.valueOf(taxRateField.getText()),
            Double.valueOf(superRateField.getText()), 
            Date.valueOf(dobField.getValue()),
            user
        );
    }
    
    private boolean allFieldsHaveValues() {
        boolean isValid = true;
        
        if(nameField.getText().isBlank()) {
            isValid = false;
        }
        if(addressField.getText().isBlank()) {
            isValid = false;
        }
        if(mobileNoField.getText().isBlank()) {
            isValid = false;
        }
        if(usernameField.getText().isBlank()) {
            isValid = false;
        }
        if(passwordField.getText().isBlank()) {
            isValid = false;
        }
        if(emailField.getText().isBlank()) {
            isValid = false;
        }
        if(bankAccField.getText().isBlank()) {
            isValid = false;
        }
        if(designationField.getText().isBlank()) {
            isValid = false;
        }
        if(hourlyRateField.getText().isBlank()) {
            isValid = false;
        }
        if(taxRateField.getText().isBlank()) {
            isValid = false;
        }
        if(superRateField.getText().isBlank()) {
            isValid = false;
        }
        if(dobField.getValue() == null) {
            isValid = false;
        }
        return isValid;
    }

    @FXML
    private void onClickBack(ActionEvent event) {
        try {
            App.setRoot("AdminView");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
