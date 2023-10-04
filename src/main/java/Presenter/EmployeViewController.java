/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Presenter;

import Utils.UserManager;
import com.mycompany.ginpayroll.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import model.User;
/**
 * FXML Controller class
 *
 * @author Chamali
 */
public class EmployeViewController implements Initializable {


    @FXML
    private Button timeAddBtn;
    @FXML
    private Button infoBtn;
    @FXML
    private Button paySlipBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onViewEditTapped(ActionEvent event) {
        try {
            App.setRoot("ViewEditEmployee");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onAddTimeClicked(ActionEvent event) {
        try {
            App.setRoot("AddTime");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onViewSlipClicked(ActionEvent event) {
        try {
            App.setRoot("ViewPayslip");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
