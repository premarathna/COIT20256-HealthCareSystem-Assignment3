/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Presenter;

import Utils.Constants;
import Utils.Helper;
import Utils.UserManager;
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
import javafx.scene.control.TextField;
import model.TimeLog;
import model.User;

/**
 * FXML Controller class
 *
 * @author Chamali
 */
public class AddTimeController implements Initializable {

    @FXML
    private DatePicker dateField;
    @FXML
    private TextField totalHoursField;
    @FXML
    private Button backToHomebtn;
    @FXML
    private Button addBtn;
    @FXML
    private TextField empIdField;

    private User user;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = UserManager.shared().getUser();
        empIdField.setText(String.valueOf(user.getEmployeeId()));
    }    

    @FXML
    private void onClickBack(ActionEvent event) {
        try {
            App.setRoot("EmployeeView");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onClickAdd(ActionEvent event) {
       String timeText = totalHoursField.getText();
        LocalDate dateVal = dateField.getValue();
       // Empty input validation
        if (!timeText.isBlank() && dateVal != null) {
            // Time validation
            double time = Double.parseDouble(timeText);
            if(Helper.isARealNumber(timeText) && time > 0) {
                // Creating a TimeLog object
                Date date = Date.valueOf(dateField.getValue()); 
                TimeLog timeLog = new TimeLog(date, time);
                if (timeLog.insertTimeLog()) {
                    Helper.showAlert("Success", "Time logged successfully");
                    dateField.setValue(null);
                    totalHoursField.setText(null);
                } else {
                    Helper.showAlert("Error", "Failed to log time");
                }
            } else {
                Helper.showAlert("Invalid", "Time needs to be a vale greater than 0 and cannot include characters");
            }
        } else {
            Helper.showAlert("Invalid", "Fields cannot be empty");
        }
    }
}
