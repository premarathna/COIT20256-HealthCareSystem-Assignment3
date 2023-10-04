/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Employee;

/**
 *
 * @author Chamali
 */
public class Helper {
    public static String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
    
    public static String decryptPassword(String encodedPassword) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
        return new String(decodedBytes);
    }
    
    public static String convertDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu MMM dd");
        return date.format(formatter);
    }
    
    public static double roundOff(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedNumber = decimalFormat.format(number);
        return Double.parseDouble(formattedNumber);
    }
    
    public static boolean isPasswordValid(String password) {
        // min 8 chars, 1 uppercase, 1 lowercase, 1 special char, 1 digit
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if(password.matches(regex)) {
            return true;
        }
        return false;
    }
    
    public static boolean isEmailValid(String email) {
        String regex =  "^[A-Za-z0-9+_.-]+@(.+)$";
        if(email.matches(regex)) {
            return true;
        }
        return false;
    }
    
    public static boolean isMobileValid(String mobile) {
        //10 digita from 0 to 9
        String regex =   "^[0-9]{10}$";
        if(mobile.matches(regex)) {
            return true;
        }
        return false;
    }
    
    public static boolean isNameValid(String name) {
        String regex = "^[a-zA-Z ]*$";
        if(name.matches(regex)) {
            return true;
        }
        return false;
    }
    
    public static boolean isAccountNumberValid(String accountNumber) {
        return onlyContainsNumbers(accountNumber);
    }
    
    public static boolean onlyContainsNumbers(String input) {
        String regex = "^[0-9]*$";
        if(input.matches(regex)) {
            return true;
        }
        return false;
    }
    
    public static boolean isARealNumber(String input) {
        String regex = "^[+-]?\\d+(\\.\\d+)?$";
        if(input.matches(regex)) {
            return true;
        }
        return false;
    }
    
    public static void showAlert (String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
