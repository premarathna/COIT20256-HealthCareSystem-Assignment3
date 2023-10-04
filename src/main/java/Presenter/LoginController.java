/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Presenter;

import Utils.Constants;
import Utils.DbConnectionManager;
import Utils.Helper;
import Utils.UserManager;
import com.mycompany.ginpayroll.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
/**
 * FXML Controller class
 *
 * @author Chamali
 */
public class LoginController implements Initializable {


    @FXML
    private Button loginBtn;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void didClickLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        // Get user instance for the given username with values returned 
        // This constructor fetches user Login data from DB and sets them to itself
        User user = new User(username);
  
        // Check if password found for given username
        if(user.getEncryptedPassword() != null) {
            String decodedPassword = user.getDecryptedPassword();
            // Check if entered password is equal to decoded password fetched from db
            if(decodedPassword.equals(password)) {
                // Setting the current user object to the userManager singleton instance
                UserManager.shared().setUser(user);

                if (user.getRole().equals(Constants.adminRole)) {
                    try {
                        App.setRoot("AdminView");
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                } else {
                    try {
                        App.setRoot("EmployeeView");
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            } else {
                Helper.showAlert("Error", "Password does not match username");
            }
        } else {
            Helper.showAlert("Error", "Incorrect username");
        }
    }   
}
