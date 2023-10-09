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
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author N A A S Senanayake
 */
public class FinancialReportViewController implements Initializable {

    @FXML
    private ComboBox<String> cmbMonth;
    @FXML
    private Button btnBackToHome;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnGenerate;
    @FXML
    private ComboBox<Integer> cmbYear;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbMonth.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        cmbYear.getItems().addAll(2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023);
    }

    @FXML
    private void backToHome(ActionEvent event) {
    }

    @FXML
    private void back(ActionEvent event) {
        try {
            App.setRoot("ReportView");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void generateReport(ActionEvent event) {
    }

}
