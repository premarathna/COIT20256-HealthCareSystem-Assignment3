package com.mycompany.ginpayroll;

import Utils.DbConnectionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Appointment;
import model.Invoice;
import model.Patient;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Stage primaryStage;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        scene = new Scene(loadFXML("CreateAppointment"), 905, 568);//Login
//        scene = new Scene(loadFXML("AdminView"), 905, 568);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        // Create required tables if they do not exist
        DbConnectionManager dbManager = DbConnectionManager.shared();
        dbManager.createTablesIfNeeded();
        launch();
    }
}
