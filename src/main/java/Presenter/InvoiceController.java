package Presenter;

import Utils.PdfService;
import com.mycompany.ginpayroll.App;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.scene.web.WebView;

import model.Appointment;
import model.Invoice;
import model.InvoiceTable;
import model.Patient;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class InvoiceController implements Initializable {
    private String invoiceIdToUpdate;

    @FXML
    private TableView<InvoiceTable> tableViewInvoice;
    @FXML
    private TableColumn<InvoiceTable, String> indexColumn;
    @FXML
    private TableColumn<InvoiceTable, String> invoiceColumn;
    @FXML
    private TableColumn<InvoiceTable, String> dateTimeColumn;
    @FXML
    private TableColumn<InvoiceTable, String> totalColumn;
    @FXML
    private TableColumn<InvoiceTable, String> statusColumn;
    @FXML
    private Label lblAppointment;
    @FXML
    private Label lblGrossTotal;
    @FXML
    private Label lblInvoiceDiscount;
    @FXML
    private Label lblInvoiceTotal;
    @FXML
    private Label lblPaidAmount;
    @FXML
    private Label lblInvoiceStatus;
    @FXML
    private Label lblPatient;
    @FXML
    private Label lblInvoiceDate;
    @FXML
    private Button printButton;
    @FXML
    private Button payButton;
    @FXML
    private Button clearButton;
    @FXML
    private TextField txtDiscount;
    @FXML
    private TextField txtPayAmount;

    private final ObservableList<InvoiceTable> invoiceDataList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
        resetForm();
    }

    private void initInvoiceTable(){
        indexColumn.setCellValueFactory(cellData -> cellData.getValue().indexProperty());
        invoiceColumn.setCellValueFactory(cellData -> cellData.getValue().invoiceDescriptionProperty());
        dateTimeColumn.setCellValueFactory(cellData -> cellData.getValue().createdAtProperty());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().isPaidProperty());

        TableColumn<InvoiceTable, String> actionColumn = new TableColumn<>("");
        actionColumn.setCellFactory(createButtonColumn());
        tableViewInvoice.getColumns().add(actionColumn);

        tableViewInvoice.setItems(invoiceDataList);
    }

    private void fillInvoiceView(String invoiceId){
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        Invoice invoice = Invoice.findInvoiceById(Integer.parseInt(invoiceId));
        Appointment appointment = Appointment.findAppointmentById(invoice.getAppointmentId());
        Patient patient = Patient.findPatientById(appointment.patientId);

        lblAppointment.setText("Appointment : Doctors Appointment | "+appointment.appointmentId);
        lblGrossTotal.setText("Gross Total : "+currencyFormat.format(invoice.getGrossTotal()));
        lblInvoiceDiscount.setText("Discount : "+currencyFormat.format(invoice.getDiscountedTotal()));
        lblInvoiceTotal.setText("Total : "+currencyFormat.format(invoice.getTotal()));
        lblPaidAmount.setText("Paid Amount : "+currencyFormat.format(invoice.getPaidAmount()));
        lblInvoiceStatus.setText("Status : "+(invoice.isPaid() ? "Paid" : "Pending Payment"));
        lblPatient.setText("Patient : "+patient.getAvailableBName());
        lblInvoiceDate.setText("Date : "+invoice.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss")));

        if (invoice.isPaid()){
            disableTextFieldStatus(true);
            disableButtonStatus(false);
            payButton.setDisable(true);
        }else {
            disableTextFieldStatus(false);
            disableButtonStatus(false);
            txtDiscount.setText(invoice.getDiscountedTotal().toString());
        }

    }

    @FXML
    public void makePaymentToInvoice(){
        System.out.println("make payment = "+invoiceIdToUpdate);
        int invoiceId = Integer.parseInt(invoiceIdToUpdate);
        BigDecimal paidAmount = new BigDecimal(txtPayAmount.getText());
        BigDecimal discountAmount = new BigDecimal(txtDiscount.getText());
        Invoice invoice = Invoice.markInvoiceAsPaid(invoiceId,paidAmount,discountAmount);
        if (invoice == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("error");
            alert.setHeaderText("Invoice Error");
            alert.setContentText("Failed to make payment to invoice");
            ButtonType okButton = new ButtonType("OK");
            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Success");
            alert.setHeaderText("Payment Success");
            alert.setContentText("Successfully made payment to the invoice");
            ButtonType okButton = new ButtonType("OK");
            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait();

            loadTable();
            resetForm();
        }
    }

    @FXML
    public void generatePdf() {
        PdfService pdfService = new PdfService();
        File pdf = pdfService.generateInvoicePdf(Integer.parseInt(invoiceIdToUpdate));

        if (pdf == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("error");
            alert.setHeaderText("PDF Error");
            alert.setContentText("Failed to create PDF");
            ButtonType okButton = new ButtonType("OK");
            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Success");
            alert.setHeaderText("PDF Create Success");
            alert.setContentText("Successfully create PDF to the invoice");
            ButtonType okButton = new ButtonType("OK");
            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait();
        }

        String pdfPath = pdf.getAbsolutePath();
        System.out.println("path to pdf : "+pdf.getAbsolutePath());
    }

    public void setDefaultLabelValues() {
        lblAppointment.setText("Appointment : ");
        lblGrossTotal.setText("Gross Total : ");
        lblInvoiceDiscount.setText("Discount : ");
        lblInvoiceTotal.setText("Total : ");
        lblPaidAmount.setText("Paid Amount : ");
        lblInvoiceStatus.setText("Status : ");
        lblPatient.setText("Patient : ");
        lblInvoiceDate.setText("Date : ");
    }

    private void loadTable(){
        initInvoiceTable();
        List<Invoice> invoiceList = Invoice.getAllInvoices();
        final int[] index = {0};
        invoiceDataList.clear();
        invoiceList.forEach(invoice ->{
            index[0]++;
            invoiceDataList.add(InvoiceTable.init(invoice, index[0]));
        });
    }

    public void disableButtonStatus(boolean disable) {
        printButton.setDisable(disable);
        payButton.setDisable(disable);
        clearButton.setDisable(disable);
    }

    public void disableTextFieldStatus(boolean disable) {
        txtDiscount.setDisable(disable);
        txtPayAmount.setDisable(disable);
    }

    public void clearTextFields() {
        txtDiscount.clear();
        txtPayAmount.clear();
    }

    public void resetForm(){
        invoiceIdToUpdate = null;
        setDefaultLabelValues();
        clearTextFields();
        disableTextFieldStatus(true);
        disableButtonStatus(true);
    }

    private Callback<TableColumn<InvoiceTable, String>, TableCell<InvoiceTable, String>> createButtonColumn() {
        return new Callback<TableColumn<InvoiceTable, String>, TableCell<InvoiceTable, String>>() {
            @Override
            public TableCell<InvoiceTable, String> call(TableColumn<InvoiceTable, String> param) {
                return new TableCell<InvoiceTable, String>() {
                    final Button button = new Button("view");

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            setGraphic(button);
                            button.setOnAction(event -> {
                                InvoiceTable invoiceTable = getTableView().getItems().get(getIndex());
                                invoiceIdToUpdate = invoiceTable.getInvoiceId();
                                System.out.println("Fill Form invoice ID: " + invoiceIdToUpdate);
                                fillInvoiceView(invoiceIdToUpdate);
                            });
                        }
                    }
                };
            }
        };
    }


}
