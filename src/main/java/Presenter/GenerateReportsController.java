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
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.PayrollReport;
/**
 * FXML Controller class
 *
 * @author Chamali
 */
public class GenerateReportsController implements Initializable {


    @FXML
    private DatePicker startDatePicker;
    @FXML
    private Button backtoHomeBtn;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Button generateBtn;
    @FXML
    private Label totalNetPayLabel;
    @FXML
    private Label taxLabel;
    @FXML
    private Label superLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onClickBack(ActionEvent event) {
        try {
            App.setRoot("AdminView");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onClickGeneratePay(ActionEvent event) {
            LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        // Empty input validation
        if (startDate != null && endDate != null) {
            if (startDate.isBefore(endDate)) {
                Date sqlStartDate = Date.valueOf(startDate);
                Date sqlEndDate = Date.valueOf(endDate);
                PayrollReport report = new PayrollReport(sqlStartDate, sqlEndDate);
                // This methods fetches payroll report data and updates values on model
                boolean success = report.fetchAndUpdatePayrollReport();
                if(success) {
                    double totaNetPay = Helper.roundOff(report.totalNetPay);
                    double totalTaxPay = Helper.roundOff(report.totalTaxPay); 
                    double totalSuperPay = Helper.roundOff(report.totalSuperPay);
                    
                    totalNetPayLabel.setText(Double.toString(totaNetPay));
                    taxLabel.setText(Double.toString(totalTaxPay));
                    superLabel.setText(Double.toString(totalSuperPay));
                } else {
                    Helper.showAlert("Something went wrong", "Failed to fetch payroll report data.");
                }
            } else {
                Helper.showAlert("Invalid Date Range", "The end date should be greater than the start date.");
            }
        } else {
            Helper.showAlert("Invalid", "Please pick start and end dates before generating payroll report.");
        }
    }

}
