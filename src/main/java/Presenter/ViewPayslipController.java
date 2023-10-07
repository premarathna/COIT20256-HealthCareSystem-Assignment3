///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
// */
//package Presenter;
//
//import Utils.Helper;
//import Utils.UserManager;
//import com.mycompany.ginpayroll.App;
//import java.io.IOException;
//import java.net.URL;
//import java.sql.Date;
//import java.time.LocalDate;
//import java.util.ResourceBundle;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.DatePicker;
//import javafx.scene.control.Label;
//import model.PaySlip;
//
///**
// * FXML Controller class
// *
// * @author Chamali
// */
//public class ViewPayslipController implements Initializable {
//
//    @FXML
//    private DatePicker startDatePicker;
//    @FXML
//    private Button backtoHomeBtn;
//    @FXML
//    private DatePicker endDatePicker;
//    @FXML
//    private Button generateBtn;
//    @FXML
//    private Label nameLabel;
//    @FXML
//    private Label baseRateLabel;
//    @FXML
//    private Label startDateLabel;
//    @FXML
//    private Label enaDateLabel;
//    @FXML
//    private Label totalHoursLabel;
//    @FXML
//    private Label amountLabel;
//    @FXML
//    private Label taxDeductionLabel;
//    @FXML
//    private Label totalPayLabel;
//    @FXML
//    private Label superAmountLabel;
//
//    /**
//     * Initializes the controller class.
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//    }    
//
//    @FXML
//    private void onClickBack(ActionEvent event) {
//        try {
//            App.setRoot("EmployeeView");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    @FXML
//    private void onClickGeneratePay(ActionEvent event) {
//        LocalDate startDate = startDatePicker.getValue();
//        LocalDate endDate = endDatePicker.getValue();
//        // Empty input validation
//        if (startDate != null && endDate != null) {
//            if (startDate.isBefore(endDate)) {
//                Date sqlStartDate = Date.valueOf(startDate);
//                Date sqlEndDate = Date.valueOf(endDate);
//                PaySlip slip = new PaySlip(sqlStartDate, sqlEndDate);
//                // This methods fetched palyslip data and updates values on model
//                boolean success = slip.fetchAndUpdatePalySlip();
//                if(success) {
//                    double totalPay = Helper.roundOff(slip.hourlyRate * slip.totalHours);
//                    double taxDeducation = Helper.roundOff(totalPay * (slip.taxRate / 100)); 
//                    double netPay = Helper.roundOff(totalPay - taxDeducation);
//                    double superAmount = Helper.roundOff(totalPay *(slip.superRate / 100));
//                    
//                    nameLabel.setText(slip.name);
//                    baseRateLabel.setText(Double.toString(slip.hourlyRate));
//                    startDateLabel.setText(Helper.convertDateToString(startDate));
//                    enaDateLabel.setText(Helper.convertDateToString(endDate));
//                    totalHoursLabel.setText(Double.toString(slip.totalHours));
//                    amountLabel.setText(Double.toString(totalPay));
//                    taxDeductionLabel.setText(Double.toString(taxDeducation));
//                    totalPayLabel.setText(Double.toString(netPay));
//                    superAmountLabel.setText(Double.toString(superAmount));
//                } else {
//                    Helper.showAlert("Something went wrong", "Failed to fetch paylip data for the user.");
//                }
//            } else {
//                Helper.showAlert("Invalid Date Range", "The end date should be greater than the start date.");
//            }
//        } else {
//            Helper.showAlert("Invalid", "Please pick start and end dates before generating the slip.");
//        }
//    }
//}
