module com.mycompany.ginpayroll {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    //requires de.jensd.fx.fontawesomefx.fontawesome;

    opens com.mycompany.ginpayroll to javafx.fxml;
    exports com.mycompany.ginpayroll;
    opens Presenter to javafx.fxml;
    exports Presenter;
    requires jasperreports;
    
}
