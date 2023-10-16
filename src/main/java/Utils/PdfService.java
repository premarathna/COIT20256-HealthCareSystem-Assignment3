package Utils;

import model.Appointment;
import model.Invoice;
import model.Patient;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PdfService {

    private final String invoiceFolderPath = "D:/COIT20258/Ass3/Inovice_PDF";

   public File generateInvoicePdf(int invoiceid) {
       try {

           System.out.println("Generate PDF for invoice = " + invoiceid);

           Invoice invoice = Invoice.findInvoiceById(invoiceid);
           Appointment appointment = Appointment.findAppointmentById(invoice.getAppointmentId());
           Patient patient = Patient.findPatientById(appointment.patientId);
           NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
           DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy - MMMM - dd - EEEE | hh:mm:ss a");

           PDDocument document = new PDDocument();
           PDPage page = new PDPage();
           document.addPage(page);
           PDPageContentStream contentStream = new PDPageContentStream(document, page);


           String invoiceNumber = "INV-" + invoice.getInvoiceId();
           String invoiceStatus = invoice.isPaid() ? "Paid" : "Pending Payment";
           String invoicePaidDate = invoice.isPaid() ? dateTimeFormatter.format(invoice.getPaidAt()) : "";

           contentStream.setFont(PDType1Font.HELVETICA, 12);

           contentStream.beginText();
           contentStream.newLineAtOffset(50, 750);
           contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
           contentStream.showText("Invoice");
           contentStream.endText();

           contentStream.beginText();
           contentStream.newLineAtOffset(50, 730);
           contentStream.setFont(PDType1Font.HELVETICA, 12);
           contentStream.showText("Invoice Number : "+invoiceNumber);
           contentStream.endText();

           contentStream.moveTo(0, 720);
           contentStream.lineTo(800    , 720);
           contentStream.stroke();

           contentStream.beginText();
           contentStream.newLineAtOffset(50, 700);
           contentStream.setFont(PDType1Font.HELVETICA, 12);
           contentStream.showText("Patient : "+patient.getAvailableBName());
           contentStream.endText();

           contentStream.beginText();
           contentStream.newLineAtOffset(270, 700);
           contentStream.setFont(PDType1Font.HELVETICA, 12);
           contentStream.showText("Date : "+dateTimeFormatter.format(invoice.getCreatedAt()));
           contentStream.endText();

           contentStream.moveTo(0, 690);
           contentStream.lineTo(800    , 690);
           contentStream.stroke();

           contentStream.beginText();
           contentStream.newLineAtOffset(50, 650);
           contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
           contentStream.showText("Description");
           contentStream.endText();

           contentStream.beginText();
           contentStream.newLineAtOffset(400, 650);
           contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
           contentStream.showText("Amount");
           contentStream.endText();

           contentStream.beginText();
           contentStream.newLineAtOffset(50, 630);
           contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
           contentStream.showText("Doctors Appointment | Reference : "+appointment.appointmentId);
           contentStream.endText();

           contentStream.beginText();
           contentStream.newLineAtOffset(400, 630);
           contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
           contentStream.showText(currencyFormat.format(appointment.amountToCharge));
           contentStream.endText();

           contentStream.beginText();
           contentStream.newLineAtOffset(50, 500);
           contentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 12);
           contentStream.showText("Invoice Status : "+ invoiceStatus);
           contentStream.endText();

           contentStream.beginText();
           contentStream.newLineAtOffset(50, 480);
           contentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 12);
           contentStream.showText("Payed Date : "+ invoicePaidDate);
           contentStream.endText();

           contentStream.beginText();
           contentStream.newLineAtOffset(400, 580);
           contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
           contentStream.showText("Total Amount : "+currencyFormat.format(invoice.getTotal()));
           contentStream.endText();

           contentStream.beginText();
           contentStream.newLineAtOffset(400, 560);
           contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
           contentStream.showText("Discount Amount : "+currencyFormat.format(invoice.getDiscountedTotal()));
           contentStream.endText();

           contentStream.beginText();
           contentStream.newLineAtOffset(400, 540);
           contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
           contentStream.showText("Gross Amount : "+currencyFormat.format(invoice.getGrossTotal()));
           contentStream.endText();

           contentStream.beginText();
           contentStream.newLineAtOffset(50, 430);
           contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 16);
           contentStream.showText("Thank You!");
           contentStream.endText();

           contentStream.beginText();
           contentStream.newLineAtOffset(330, 400);
           contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
           contentStream.showText("Print Date : "+dateTimeFormatter.format(LocalDateTime.now()));
           contentStream.endText();

           contentStream.moveTo(0, 390);
           contentStream.lineTo(800    , 400);
           contentStream.stroke();

           contentStream.close();

           String filePath = invoiceFolderPath + File.separator + "INV-" + invoiceid + ".pdf";
           File pdfFile = new File(filePath);
           document.save(pdfFile);
           document.close();

           System.out.println("Invoice PDF created successfully at: " + filePath);
           return pdfFile;
       } catch (IOException e) {
           e.printStackTrace();
           return null; // Return null if there's an error
       }
   }
}
