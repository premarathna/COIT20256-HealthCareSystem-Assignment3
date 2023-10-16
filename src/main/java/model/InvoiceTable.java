package model;

import com.mycompany.ginpayroll.App;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class InvoiceTable {

    private StringProperty index;
    private StringProperty invoiceId;
    private StringProperty invoiceDescription;
    private StringProperty createdAt;
    private StringProperty total;
    private StringProperty isPaid;

    public static InvoiceTable init(Invoice invoice,int index){
        return new InvoiceTable(index,invoice.getInvoiceId(),invoice.getCreatedAt(),invoice.getTotal(),invoice.isPaid(), invoice.getAppointmentId());
    }

    public InvoiceTable(int index,int invoiceId, LocalDateTime createdAt, BigDecimal total, boolean isPaid,int appointmentid) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        this.index = new SimpleStringProperty(String.valueOf(index));
        this.invoiceId = new SimpleStringProperty(String.valueOf(invoiceId));
        this.invoiceDescription = new SimpleStringProperty("INV-"+invoiceId+" | Doctors Appointment | Reference : "+appointmentid);
        this.createdAt = new SimpleStringProperty(createdAt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss")));
        this.total = new SimpleStringProperty(currencyFormat.format(total));
        this.isPaid = new SimpleStringProperty( isPaid ? "Paid" : "Pending Payment");
    }

    public StringProperty indexProperty() {
        return index;
    }

    public String getInvoiceId() {
        return invoiceId.get();
    }

    public StringProperty invoiceIdProperty() {
        return invoiceId;
    }

    public StringProperty invoiceDescriptionProperty() {
        return invoiceDescription;
    }



    public void setInvoiceId(String invoiceId) {
        this.invoiceId.set(invoiceId);
    }

    public String getCreatedAt() {
        return createdAt.get();
    }

    public StringProperty createdAtProperty() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt.set(createdAt);
    }

    public String getTotal() {
        return total.get();
    }

    public StringProperty totalProperty() {
        return total;
    }

    public void setTotal(String total) {
        this.total.set(total);
    }

    public String getIsPaid() {
        return isPaid.get();
    }

    public StringProperty isPaidProperty() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid.set(isPaid);
    }
}
