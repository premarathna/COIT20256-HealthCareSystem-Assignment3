/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Presenter;

import Utils.DbConnectionManager;
import Utils.UserManager;
import com.mycompany.ginpayroll.App;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.User;
/**
 * FXML Controller class
 *
 * @author User
 */
public class ChangeAvailabilityController implements Initializable {


    @FXML
    private Button backToHomeBtn;
    @FXML
    private Button changeBtn;
    @FXML
    private ComboBox<String> availabilityComboBox;
    @FXML
    private Label dateandtimelabel;
    @FXML
    private Label doctorAvaliabilityLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          // Add the options to the ComboBox
        availabilityComboBox.getItems().addAll("All Days", "Weekend", "Weekdays");

        // Set the default selection to "All Days"
        availabilityComboBox.setValue("All Days");
    }    
    
    @FXML
    private void didClickBackToHome(ActionEvent event) {
            try {
            // Set the root view to the "DoctorView" (Home) when the button is clicked.
            App.setRoot("DoctorView");
        } catch (IOException ex) {
            // Handle any IOException that may occur during the navigation.
            ex.printStackTrace(); // For debugging purposes; consider proper error handling.
        }}
    

    @FXML
    private void didClickChange(ActionEvent event) {
            // Retrieve the userId of the currently logged-in doctor (you'll need to replace this with the actual user ID retrieval)
        int userId = getCurrentDoctorUserId();

        // Get the selected availability
        String selectedAvailability = availabilityComboBox.getValue();

        // Update the Doctor table for doctors with the same userId
        boolean success = updateDoctorAvailability(userId, selectedAvailability);

        if (success) {
            // Show a success message
            doctorAvaliabilityLabel.setText("Availability updated successfully!");
        } else {
            // Show an error message
            doctorAvaliabilityLabel.setText("Failed to update availability.");
        }
    }
    
    // Replace this with your logic to get the current doctor's user ID
    private int getCurrentDoctorUserId() {

        // Get the currently logged-in user from the UserManager
        UserManager userManager = UserManager.shared();
        User currentUser = userManager.getUser();

        if (currentUser.getRole().equalsIgnoreCase("Doctor")) {
            return currentUser.getUserId();
        }

        // Return -1 or another appropriate value to indicate failure or no doctor found.
        return -1;
    }

    // Implement your logic to update the Doctor table with the selected availability
    private boolean updateDoctorAvailability(int userId, String selectedAvailability) {
        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            String updateQuery = "UPDATE Doctor SET availability = ? WHERE userId = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, selectedAvailability);
            preparedStatement.setInt(2, userId);

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0; // Return true if at least one row is updated, false on failure.
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle exceptions and return false on failure
        }
    }

}
