/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Presenter;

import Utils.DbConnectionManager;
import Utils.TableName;
import com.mycompany.ginpayroll.App;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
/**
 * FXML Controller class
 *
 * @author User
 */
public class HelthRecordController implements Initializable {


    @FXML
    private Button backToHomeBtn;
    @FXML
    private ComboBox<String> PatientcomboBox;
    @FXML
    private Text message;
    @FXML
    private TextArea recordText;
    /**
     * Initializes the controller class.
     */
      @Override
    public void initialize(URL url, ResourceBundle rb) {
    // Initialize the PatientcomboBox with patient first names
    DbConnectionManager dbConnectionManager = DbConnectionManager.shared();
    PatientcomboBox.getItems().addAll(getAllPatientFirstNames());

    // Attach an event handler to PatientcomboBox for selection changes
    PatientcomboBox.setOnAction(event -> loadPatientRecords());

    }
public List<String> getAllPatientFirstNames() {
    List<String> patientFirstNames = new ArrayList<>();

    try {
        String query = "SELECT `fName` FROM " + TableName.patient_report;
        Connection connection = DbConnectionManager.shared().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String fName = resultSet.getString("fName");
            patientFirstNames.add(fName);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return patientFirstNames;
}
    private void loadPatientRecords() {
        // Get the selected patient from the ComboBox
        String selectedPatient = PatientcomboBox.getValue();

        if (selectedPatient != null) {
            // Query the database for the patient's records based on the selected patient
            String patientRecords = queryPatientRecords(selectedPatient);

            // Display the patient records in the TextArea
            recordText.setText(patientRecords);
        } else {
            // Handle the case where no patient is selected
            recordText.setText("Please select a patient.");
        }
    }

    private String queryPatientRecords(String selectedPatient) {
        // Implement the database query to retrieve the patient's records
        String records = "";

        // Define your database connection and query here
        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            String query = "SELECT `fName`, `lName`, `month`, `disease`, `age`, `phone`, `email`, `address`, `dateCreated`, `insuranceId` FROM patient_report WHERE `fName` = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, selectedPatient);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String fName = resultSet.getString("fName");
                String lName = resultSet.getString("lName");
                String month = resultSet.getString("month");
                String disease = resultSet.getString("disease");
                int age = resultSet.getInt("age");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String dateCreated = resultSet.getString("dateCreated");
                String insuranceId = resultSet.getString("insuranceId");

                // Append the details to the records string
                records += "First Name: " + fName + "\n";
                records += "Last Name: " + lName + "\n";
                records += "Month: " + month + "\n";
                records += "Disease: " + disease + "\n";
                records += "Age: " + age + "\n";
                records += "Phone: " + phone + "\n";
                records += "Email: " + email + "\n";
                records += "Address: " + address + "\n";
                records += "Date Created: " + dateCreated + "\n";
                records += "Insurance ID: " + insuranceId + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            records = "Error: Failed to retrieve records.";
        }

        return records;
    }

    
    @FXML
    private void didClickBackToHome(ActionEvent event) {
            try {
            // Set the root view to the "DoctorView" (Home) when the button is clicked.
            App.setRoot("DoctorView");
        } catch (IOException ex) {
            // Handle any IOException that may occur during the navigation.
            ex.printStackTrace(); // For debugging purposes; consider proper error handling.
        }
    }
    

}
