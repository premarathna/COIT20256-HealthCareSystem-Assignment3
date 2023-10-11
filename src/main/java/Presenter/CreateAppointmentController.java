/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Presenter;

import Utils.DbConnectionManager;
import Utils.Helper;
import com.mycompany.ginpayroll.App;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Appointment;
/**
 * FXML Controller class
 *
 * @author User
 */
public class CreateAppointmentController implements Initializable {


    @FXML
    private Button proceedBillingBtn;
    @FXML
    private Button backToHomeBtn;
    @FXML
    private Button createAppointmentBtn;
    @FXML
    private ComboBox<String> DateComboBox;
    @FXML
    private ListView<String> timeListView;
    @FXML
    private ComboBox<String> DoctorComboBox;
    @FXML
    private ComboBox<String> PatientcomboBox;
        private DbConnectionManager dbConnectionManager;
    @FXML
    private Text message;
    /**
     * 
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Create a list of dates
        ObservableList<String> dates = FXCollections.observableArrayList(
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
        );
  // Initialize the dbConnectionManager
        dbConnectionManager = DbConnectionManager.shared();
        // Set the list of dates to the ComboBox
        DateComboBox.setItems(dates);

        // Set an initial value if needed
        DateComboBox.getSelectionModel().select(0);
        
        // Create a list of time options
        List<String> timeOptions = createTimeOptions();

        // Populate the ComboBox with the time options
        timeListView.getItems().addAll(timeOptions);
        
            // Fetch patient names and doctor names
        List<String> patientNames = DbConnectionManager.shared().getAllPatientNames();
        List<String> doctorNames = DbConnectionManager.shared().getAllDoctorNames();

        // Populate the ComboBox widgets
        PatientcomboBox.getItems().addAll(patientNames);
        DoctorComboBox.getItems().addAll(doctorNames);
        proceedBillingBtn.setVisible(false);
    }    
    
    // Method to create the time range options
    private List<String> createTimeOptions() {
        // Initialize a list to hold the time options
        List<String> timeOptions = new ArrayList<>();

        // Define the time ranges
        String[] timeRanges = {
            "4:00 PM - 4:30 PM",
            "4:30 PM - 5:00 PM",
            "5:00 PM - 5:30 PM",
            "5:30 PM - 6:00 PM"
        };

        // Add the time ranges to the list of time options
        timeOptions.addAll(Arrays.asList(timeRanges));

        // Return the list of time options
        return timeOptions;
    }      
    
     @FXML
     private void didClickCreateAppointment(ActionEvent event) {
     
        // Get selected doctor and patient names from ComboBoxes
        String selectedDoctorName = DoctorComboBox.getValue();
        String selectedPatientName = PatientcomboBox.getValue();

        // Fetch the corresponding doctorId and patientId based on their names
        int doctorId = getDoctorIdByName(selectedDoctorName);
        int patientId = getPatientIdByName(selectedPatientName);

        // Fetch the selected timeslotId (you need to implement this method)

        int timeslotId = getTimeslotId(doctorId); 
            // Get the current LocalDate
        // Get the current LocalDate
        LocalDate localDate = LocalDate.now();
        
        // Convert LocalDate to SQL Date
        Date SDate = Date.valueOf(localDate);

        // Create an appointment and insert it into the database
        if (doctorId != -1 && patientId != -1 && timeslotId != -1) {
            Appointment appointment = new Appointment(doctorId, SDate, patientId, timeslotId,  1);
            boolean appointmentAdded = appointment.insertAppointment();
            System.out.println(String.format("date: %s", SDate));
            if (appointmentAdded) {
                // Handle success
                Helper.showAlert("Success", "Appointment created successfully.");
                        // Handle success
            String successMessage = String.format("Appointment created - Patient: %s, Doctor: %s, Date: %s, Time: %s",
                    selectedPatientName, selectedDoctorName, DateComboBox.getValue(), timeListView.getSelectionModel().getSelectedItem()); // Update with actual variables

            // Show the success message
            message.setText(successMessage);

            // Show the billing button
            proceedBillingBtn.setVisible(true);

            // Hide the make appointment button
            createAppointmentBtn.setVisible(false);
            } else {
                // Handle error
                System.out.println("Failed to create an appointment.");
            }
        } else {
            // Handle case where doctor, patient, or timeslot was not found
            System.out.println("Doctor, patient, or timeslot not found.");
        }
    }



private int getPatientIdByName(String patientName) {
    try {
        Connection connection = dbConnectionManager.getConnection();
        String query = "SELECT patientId FROM patient WHERE CONCAT(fName, ' ', lName) = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, patientName);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("patientId");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // If no match is found, return -1 or another appropriate value
    return -1;
}


private int getDoctorIdByName(String doctorName) {
    try {
        Connection connection = dbConnectionManager.getConnection();
        String query = "SELECT doctorId FROM doctor WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, doctorName);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("doctorId");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // If no match is found, return -1 or another appropriate value
    return -1;
}
    private int getTimeslotId(int selectedDoctorId) {
        try {
            Connection connection = dbConnectionManager.getConnection();
            String query = "SELECT timeslotId FROM timeslot WHERE doctorId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, selectedDoctorId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("timeslotId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If no match is found, return -1 or another appropriate value
        return -1;
    }
        @FXML
    private void didClickBackToHome(ActionEvent event) {
        try {
            // Set the root view to the "AdminView" (Home) when the button is clicked.
            App.setRoot("AdminView");
        } catch (IOException ex) {
            // Handle any IOException that may occur during the navigation.
            ex.printStackTrace(); // For debugging purposes; consider proper error handling.
        }
    }

    @FXML
  private void didClickProceedBilling(ActionEvent event) {
    }

}
