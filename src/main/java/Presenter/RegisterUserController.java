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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Patient;
import model.User;
/**
 * FXML Controller class
 *
 * @author Chamali
 */
public class RegisterUserController implements Initializable {


    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button registerBtn;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private Button backToLoginBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roleComboBox.setItems(FXCollections.observableArrayList("Admin","Doctor"));
    }    
    
    @FXML
    private void didClickLRegister(ActionEvent event) {
         if (validateUserInput()) {
            User userModel = getUserModel() ;
            boolean success = userModel.insertUser();
            if(success) {
                Helper.showAlert("Success", "User registered succesfully!");
                try {
                        App.setRoot("Login");
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
            } else {
                Helper.showAlert("Error", "Failed to add User");
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
                if(Helper.isMobileValid(phoneField.getText())) {
                    //Name Validation
                    if(Helper.isNameValid(nameField.getText())) {
                        // Account number valdation
                            // Password Validation
                            if(Helper.isPasswordValid(passwordField.getText())) {
                                return true;
                            } else {
                                Helper.showAlert("Invalid", "Password Invaid, enter valida password");
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
    
    private boolean allFieldsHaveValues() {
        boolean isValid = true;
        
        if(nameField.getText().isBlank()) {
            isValid = false;
        }
         if(phoneField.getText().isBlank()) {
            isValid = false;
        }
        if(emailField.getText().isBlank()) {
            isValid = false;
        }
        if(usernameField.getText().isBlank()) {
            isValid = false;
        }
        if(passwordField.getText().isBlank()) {
            isValid = false;
        }
        if(roleComboBox.getValue() == null) {
            isValid = false;
        }
        return isValid;
    }
    
    private void clearUserInput() {
        nameField.setText(null);
        phoneField.setText(null);
        emailField.setText(null);
        usernameField.setText(null);
        passwordField.setText(null);
        roleComboBox.setValue(null);
    }
    
    private User getUserModel() {
        
        return new User(
            0,
            nameField.getText(), 
            phoneField.getText(), 
            emailField.getText(), 
            usernameField.getText(),
            passwordField.getText(),   
            roleComboBox.getValue()
        );
    }

    @FXML
    private void didClickBackToLogin(ActionEvent event) {
        try {
                App.setRoot("Login");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }

}
