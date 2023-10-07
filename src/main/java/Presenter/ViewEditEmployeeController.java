///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
// */
//package Presenter;
//
//import Utils.Constants;
//import Utils.Helper;
//import Utils.UserManager;
//import com.mycompany.ginpayroll.App;
//import java.io.IOException;
//import java.net.URL;
//import java.sql.Date;
//import java.util.ResourceBundle;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.DatePicker;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.scene.input.InputMethodEvent;
//import javafx.scene.input.KeyEvent;
//import model.Employee;
//import model.User;
//import org.w3c.dom.NameList;
//
///**
// * FXML Controller class
// *
// * @author Chamali
// */
//public class ViewEditEmployeeController implements Initializable {
//
//    @FXML
//    private DatePicker dobPicker;
//    @FXML
//    private TextArea addressField;
//    @FXML
//    private TextField mobileField;
//    @FXML
//    private TextField emailField;
//   
//    @FXML
//    private TextField nameField;
//    
//    @FXML
//    private TextField searchField;
//    @FXML
//    private Button updateButton;
//
//    @FXML
//    private TextField usernameField;
//    @FXML
//    private TextField passwordField;
//    @FXML
//    private TextField hourlyRateField;
//    @FXML
//    private TextField taxRateField;
//    @FXML
//    private TextField bankAccField;
//    @FXML
//    private TextField designationField;
//    @FXML
//    private TextField empIDField;
//    
//    private Employee employee;
//    private String role;
//    @FXML
//    private TextField superRateField;
//    
//    /**
//     * Initializes the controller class.
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        User user = null;
//        user = UserManager.shared().getUser();
//        if(user != null) {
//            this.role = user.getRole();
//            if(this.role.equals(Constants.employeeRole)) {
//                this.employee = Employee.fetchUserBy(user.getEmployeeId());
//                searchField.setVisible(false);
//                populateFields();
//                setFieldsDisable(false);
//            }
//        }
//    }
//    
//    @FXML
//    private void onBackTapped(ActionEvent event) {
//        String destintion = Constants.adminRole.equals(role) ? "AdminView" : "EmployeeView";
//        try {
//            App.setRoot(destintion);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    @FXML
//    private void onTextChanged(KeyEvent event) {
//        String text = searchField.getText();
//        // Search only if input is not empty AND
//        // is a number AND number > 1000 (Since emp IDs start from 1000)
//        if(!text.isEmpty() 
//                && Helper.onlyContainsNumbers(text)
//                && Integer.parseInt(text) > 1000) {
//            
//            Employee employee = Employee.fetchUserBy(Integer.parseInt(text));
//            if (employee != null) {
//                // Employee Found
//                this.employee = employee;
//                populateFields();
//            } else {
//                resetFields();
//            }
//        } else {
//            resetFields();
//        }
//    }
//    
//    @FXML
//    private void onClickUpdate(ActionEvent event) {
//        this.employee.name = nameField.getText();
//        this.employee.address = addressField.getText();
//        this.employee.dob = Date.valueOf(dobPicker.getValue());
//        this.employee.mobile = mobileField.getText();
//        this.employee.email = emailField.getText();
//        this.employee.designation = designationField.getText();
//        this.employee.hourlyRate = Double.valueOf(hourlyRateField.getText());
//        this.employee.taxRate = Double.valueOf(taxRateField.getText());
//        this.employee.superRate = Double.valueOf(superRateField.getText());
//        this.employee.accountNumber = bankAccField.getText();
//        
//       
//        if (validateUserInput()) {
//            Employee employeeModel = getEmployeeModel();
//            boolean success = employeeModel.updateUser();
//            System.out.print("=============");
//        if (success) {
//            Helper.showAlert("Success", "Employee updated succesfully!");
//        } else {
//           Helper.showAlert("Failure", "Employee upadte failed. Try Again!");
//        }
//        }
//    }
//    private void populateFields() {
//        empIDField.setText(Integer.toString(employee.employeeId));
//        nameField.setText(employee.name);
//        dobPicker.setValue(employee.dob.toLocalDate());
//        addressField.setText(employee.address);
//        mobileField.setText(employee.mobile);
//        emailField.setText(employee.email);
//        designationField.setText(employee.designation);
//        usernameField.setText(employee.user.getUsername());
//        passwordField.setText(employee.user.getDecryptedPassword());
//        hourlyRateField.setText(Double.toString(employee.hourlyRate));
//        taxRateField.setText(Double.toString(employee.taxRate));
//        superRateField.setText(Double.toString(employee.superRate));
//        bankAccField.setText(employee.accountNumber);
//        
//        setFieldsDisable(false);
//    }
//    
//    private void resetFields() {
//        empIDField.setText(null);
//        emailField.setText(null);
//        nameField.setText(null);
//        dobPicker.setValue(null);
//        addressField.setText(null);
//        mobileField.setText(null);
//        emailField.setText(null);
//        designationField.setText(null);
//        usernameField.setText(null);
//        passwordField.setText(null);
//        hourlyRateField.setText(null);
//        taxRateField.setText(null);
//        superRateField.setText(null);
//        bankAccField.setText(null);
//        
//        setFieldsDisable(true);
//    }
//    
//    private void setFieldsDisable(boolean disable) {
//        nameField.setDisable(disable);
//        dobPicker.setDisable(disable);
//        addressField.setDisable(disable);
//        mobileField.setDisable(disable);
//        emailField.setDisable(disable);
//        bankAccField.setDisable(disable);
//        
//        if(role.equals(Constants.adminRole)) {
//            designationField.setDisable(disable);
//            hourlyRateField.setDisable(disable);
//            taxRateField.setDisable(disable);
//            superRateField.setDisable(disable);
//        }
//    }
//    
//    private Employee getEmployeeModel() {
//        User user = new User(
//                usernameField.getText(), 
//                passwordField.getText(),
//                Constants.employeeRole
//        );
//        
//        return new Employee(
//            0,
//            nameField.getText(), 
//            addressField.getText(), 
//            mobileField.getText(), 
//            emailField.getText(), 
//            bankAccField.getText(), 
//            designationField.getText(), 
//            Double.valueOf(hourlyRateField.getText()), 
//            Double.valueOf(taxRateField.getText()),
//            Double.valueOf(superRateField.getText()), 
//            Date.valueOf(dobPicker.getValue()),
//            user
//        );
//    }
//    
//    private boolean allFieldsHaveValues() {
//        boolean isValid = true;
//        
//        if(nameField.getText().isBlank()) {
//            isValid = false;
//        }
//        if(addressField.getText().isBlank()) {
//            isValid = false;
//        }
//        if(mobileField.getText().isBlank()) {
//            isValid = false;
//        }
//        if(usernameField.getText().isBlank()) {
//            isValid = false;
//        }
//        if(passwordField.getText().isBlank()) {
//            isValid = false;
//        }
//        if(emailField.getText().isBlank()) {
//            isValid = false;
//        }
//        if(bankAccField.getText().isBlank()) {
//            isValid = false;
//        }
//        if(designationField.getText().isBlank()) {
//            isValid = false;
//        }
//        if(hourlyRateField.getText().isBlank()) {
//            isValid = false;
//        }
//        if(taxRateField.getText().isBlank()) {
//            isValid = false;
//        }
//        if(superRateField.getText().isBlank()) {
//            isValid = false;
//        }
//        if(dobPicker.getValue() == null) {
//            isValid = false;
//        }
//        return isValid;
//    }
//    
//    private boolean validateUserInput() {
//        if(allFieldsHaveValues()) {
//            // Email Validation
//            if(Helper.isEmailValid(emailField.getText())) {
//                // Mobile number validation
//                if(Helper.isMobileValid(mobileField.getText())) {
//                    //Name Validation
//                    if(Helper.isNameValid(nameField.getText())) {
//                        // Account number valdation
//                        if(Helper.isAccountNumberValid(bankAccField.getText())) {
//                            // Password Validation
//                            if(Helper.isPasswordValid(passwordField.getText())) {
//                                return true;
//                            } else {
//                                Helper.showAlert("Invalid", "Password Invaid, enter valida password");
//                                return false;
//                            }
//                        } else {
//                            Helper.showAlert("Invalid", "Account number can only have numbers");
//                            return false;
//                        }
//                    } else {
//                         Helper.showAlert("Invalid", "Name can only include letters");
//                         return false;
//                    }
//                } else {
//                    Helper.showAlert("Invalid", "Invalid mobile number");
//                    return false;
//                }
//            } else {
//                Helper.showAlert("Invalid", "Invalid email address");
//                return false;
//            }
//        } else {
//            Helper.showAlert("Invalid", "Fileds cannot be empty");
//            return false;
//        }
//    }
//}
