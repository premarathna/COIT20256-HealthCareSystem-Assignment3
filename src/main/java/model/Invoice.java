package model;

import Utils.DbConnectionManager;
import Utils.TableName;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private int invoiceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BigDecimal grossTotal;
    private BigDecimal discountedTotal;
    private BigDecimal total;
    private LocalDateTime paidAt;
    private BigDecimal paidAmount;
    private boolean isPaid;
    private int appointmentId;

    // Default constructor
    public Invoice() {
    }

    // Constructor with parameters
    public Invoice(int invoiceId, LocalDateTime createdAt, LocalDateTime updatedAt,
                   BigDecimal grossTotal, BigDecimal discountedTotal, BigDecimal total,
                   LocalDateTime paidAt, BigDecimal paidAmount, boolean isPaid,
                   int appointmentId) {
        this.invoiceId = invoiceId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.grossTotal = grossTotal;
        this.discountedTotal = discountedTotal;
        this.total = total;
        this.paidAt = paidAt;
        this.paidAmount = paidAmount;
        this.isPaid = isPaid;
        this.appointmentId = appointmentId;
    }


    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BigDecimal getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(BigDecimal grossTotal) {
        this.grossTotal = grossTotal;
    }

    public BigDecimal getDiscountedTotal() {
        return discountedTotal;
    }

    public void setDiscountedTotal(BigDecimal discountedTotal) {
        this.discountedTotal = discountedTotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public boolean isPaid() {
        return this.isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", grossTotal=" + grossTotal +
                ", discountedTotal=" + discountedTotal +
                ", total=" + total +
                ", paidAt=" + paidAt +
                ", paidAmount=" + paidAmount +
                ", isPaid=" + isPaid +
                ", appointmentAppointmentId=" + appointmentId +
                '}';
    }

    public static Invoice createInvoiceForAppointmentId(int appointmentId){
        Invoice invoice = findInvoiceByAppointmentId(appointmentId);
        if (invoice == null){
            System.out.println("create invoice for appointment = "+appointmentId);
            return createInvoice(init(Appointment.findAppointmentById(appointmentId)));
        }else {
            System.out.println("invoice for appointment = "+appointmentId+" already exist. invoice id ="+invoice.getInvoiceId());
            return invoice;
        }
    }

    public static Invoice createInvoice(Invoice invoice) {
        try  {
            String insertSQL = "INSERT INTO "+ TableName.invoice +
                    "(createdat, updatedat, gross_total, discounted_total, total, ispaid, paidamount, appointment_appointmentid) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            Connection connection = DbConnectionManager.shared().getConnection();

            LocalDateTime currentDateTime = LocalDateTime.now();
            BigDecimal discountedTotal = BigDecimal.ZERO;
            BigDecimal total = invoice.getGrossTotal().subtract(discountedTotal);
            boolean isPaid = false;
            BigDecimal paidAmount = BigDecimal.ZERO;

            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setTimestamp(1, Timestamp.valueOf(currentDateTime));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(currentDateTime));
            preparedStatement.setBigDecimal(3, invoice.grossTotal);
            preparedStatement.setBigDecimal(4, discountedTotal);
            preparedStatement.setBigDecimal(5, total);
            preparedStatement.setBoolean(6, isPaid);
            preparedStatement.setBigDecimal(7, paidAmount);
            preparedStatement.setInt(8, invoice.getAppointmentId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return findInvoiceById(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if the invoice creation fails
    }

    public static Invoice markInvoiceAsPaid(int invoiceId, BigDecimal paidAmount, BigDecimal discountAmount) {
        System.out.println("make payment to invoice = "+invoiceId+ " amount = "+paidAmount+" discount amount = "+discountAmount);
        String updateSQL = "UPDATE invoice " +
                "SET paidat = ?, paidamount = ?, discounted_total = ?, gross_total = ?, ispaid = ?, updatedat = ? " +
                "WHERE invoiceid = ?";
        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            boolean fullyPaid = false;

            Invoice invoiceToPay = findInvoiceById(invoiceId);
            BigDecimal grossTotal = invoiceToPay.getTotal().subtract(discountAmount);
            BigDecimal totalPaidAmount = invoiceToPay.getPaidAmount().add(paidAmount);
            BigDecimal amountNeedToPaid = grossTotal.subtract(totalPaidAmount);
            int comparisonResult = grossTotal.compareTo(totalPaidAmount);

            if (invoiceToPay.isPaid){
                System.out.println("invoice already paid. invoice id "+invoiceId);
                return invoiceToPay;
            }

            if (comparisonResult > 0) {
                System.out.println("AmountNeedToPaid is greater than Paid Amount. invoice id "+invoiceId);
                fullyPaid = false;
            } else if (comparisonResult == 0) {
                fullyPaid = true;
                System.out.println("AmountNeedToPaid is equal to Paid Amount. invoice id "+invoiceId);
            } else {
                System.out.println("AmountNeedToPaid is less than Paid Amount. invoice id "+invoiceId);
                return invoiceToPay;
            }

            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setBigDecimal(2, totalPaidAmount);
            preparedStatement.setBigDecimal(3, discountAmount);
            preparedStatement.setBigDecimal(4, grossTotal);
            preparedStatement.setBoolean(5, fullyPaid);
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(7, invoiceId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                return findInvoiceById(invoiceId);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Invoice findInvoiceById(int invoiceId) {
        System.out.println("find invoice by id = "+invoiceId);
        Invoice invoice = null;
        String query = "SELECT * FROM invoice WHERE invoiceid = ?";
        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, invoiceId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                invoice = init(resultSet);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return invoice;
    }

    public static Invoice findInvoiceByAppointmentId(int appointmentId) {
        System.out.println("find invoice by appointment id = "+appointmentId);
        Invoice invoice = null;
        String query = "SELECT * FROM invoice WHERE appointment_appointmentId = ?";
        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, appointmentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                invoice = init(resultSet);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return invoice;
    }

    private static Invoice init(ResultSet resultSet) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(resultSet.getInt("invoiceid"));
        invoice.setCreatedAt(resultSet.getTimestamp("createdat").toLocalDateTime());
        invoice.setUpdatedAt(resultSet.getTimestamp("updatedat") != null ? resultSet.getTimestamp("updatedat").toLocalDateTime() : null);
        invoice.setGrossTotal(resultSet.getBigDecimal("gross_total"));
        invoice.setDiscountedTotal(resultSet.getBigDecimal("discounted_total"));
        invoice.setTotal(resultSet.getBigDecimal("total"));
        invoice.setPaidAt(resultSet.getTimestamp("paidat") != null ? resultSet.getTimestamp("paidat").toLocalDateTime() : null);
        invoice.setPaidAmount(resultSet.getBigDecimal("paidamount"));
        invoice.setPaid(resultSet.getBoolean("ispaid"));
        invoice.setAppointmentId(resultSet.getInt("appointment_appointmentId"));
        return invoice;
    }

    public static Invoice init(Appointment appointment) {
        Invoice invoice = new Invoice();
        invoice.setGrossTotal(appointment.amountToCharge);
        invoice.setAppointmentId(appointment.appointmentId);
        return invoice;
    }

    public static List<Invoice> getAllInvoices() {
        System.out.println("get all invoices from database");
        List<Invoice> invoices = new ArrayList<>();
        String selectSQL = "SELECT * FROM invoice";
        try {
            Connection connection = DbConnectionManager.shared().getConnection();
            PreparedStatement statement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(resultSet.getInt("invoiceid"));
                invoice.setCreatedAt(resultSet.getTimestamp("createdat").toLocalDateTime());
                invoice.setUpdatedAt(resultSet.getTimestamp("updatedat") != null ? resultSet.getTimestamp("updatedat").toLocalDateTime() : null);
                invoice.setGrossTotal(resultSet.getBigDecimal("gross_total"));
                invoice.setDiscountedTotal(resultSet.getBigDecimal("discounted_total"));
                invoice.setTotal(resultSet.getBigDecimal("total"));
                invoice.setPaidAt(resultSet.getTimestamp("paidat") != null ? resultSet.getTimestamp("paidat").toLocalDateTime() : null);
                invoice.setPaidAmount(resultSet.getBigDecimal("paidamount"));
                invoice.setPaid(resultSet.getBoolean("ispaid"));
                invoice.setAppointmentId(resultSet.getInt("appointment_appointmentId"));
                invoices.add(invoice);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return invoices;

    }
}
