package miscellaneous.createPdf;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import miscellaneous.java.UserData;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

public class CreatePDF {

    private String passengerName;
    private String seats;
    private String boardingPoint;
    private String reportingTime;
    private String coachNo;
    private String tripDate;
    private String totalFare;
    private String destination;
    private String departureTime;
    private String coachType;
    private String utkNo;

    public CreatePDF(String passengerName, String seats, String boardingPoint, String reportingTime, String coachNo, String tripDate, String totalFare, String destination, String departureTime, String coachType, String utkNo) {
        this.passengerName = passengerName;
        this.seats = seats;
        this.boardingPoint = boardingPoint;
        this.reportingTime = reportingTime;
        this.coachNo = coachNo;
        this.tripDate = tripDate;
        this.totalFare = totalFare;
        this.destination = destination;
        this.departureTime = departureTime;
        this.coachType = coachType;
        this.utkNo = utkNo;
        try {
            UserData userData = new UserData();

            String fileName = "E:\\CSE 3104\\Project\\SupluxBusTicketManagementSystem\\Generated Ticket\\"+ utkNo + ".pdf";

            String image = "image/logo.jpg";

            ImageData data = ImageDataFactory.create(image);
            ImageData data1 = ImageDataFactory.create("image/logo.jpg");

            //System.out.println(passengerName + " | "+seats + " | "+ boardingPoint + " | "+ reportingTime + " | "+ coachNo + " | "+ tripDate + " | "+ totalFare + " | "+ departureTime + " | "+ destination + " | "+ coachType + " | "+ utkNo );



            Image image1 = new Image(data1);

            //System.out.println(passengerName + " | "+seats + " | "+ boardingPoint + " | "+ reportingTime + " | "+ coachNo + " | "+ tripDate + " | "+ totalFare + " | "+ departureTime + " | "+ destination + " | "+ coachType + " | "+ utkNo );

            PdfWriter pdfWriter = new PdfWriter(fileName);

            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            //pdfDocument.addNewPage();
            System.out.println("asdad");
            Document document = new Document(pdfDocument);
            pdfDocument.setDefaultPageSize(PageSize.A5.rotate());
            document.setMargins(0f, 0f, 0f, 0f);

            //System.out.println(passengerName + " | "+seats + " | "+ boardingPoint + " | "+ reportingTime + " | "+ coachNo + " | "+ tripDate + " | "+ totalFare + " | "+ departureTime + " | "+ destination + " | "+ coachType + " | "+ utkNo );



//            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
//            //PdfDocument pdfDocument = new PdfDocument(pdfWriter);
//
//            System.out.println(passengerName + " | "+seats + " | "+ boardingPoint + " | "+ reportingTime + " | "+ coachNo + " | "+ tripDate + " | "+ totalFare + " | "+ departureTime + " | "+ destination + " | "+ coachType + " | "+ utkNo );
//
//            Document document = new Document(pdfDocument);
//            //Document document = new Document(pdfDocument);
//
//            System.out.println(passengerName + " | "+seats + " | "+ boardingPoint + " | "+ reportingTime + " | "+ coachNo + " | "+ tripDate + " | "+ totalFare + " | "+ departureTime + " | "+ destination + " | "+ coachType + " | "+ utkNo );
//
//            pdfDocument.setDefaultPageSize(PageSize.A5.rotate());
//            //pdfDocument.setDefaultPageSize(PageSize.A5.rotate());
//
//            document.setMargins(0f, 0f, 0f, 0f);
//            //document.setMargins(0f, 0f, 0f, 0f);
//
////            Document document = new Document(new PdfDocument(new PdfWriter(fileName)));
//            System.out.println();


            document.add(image1);
            //document.add(image1);

            //System.out.println(passengerName + " | "+seats + " | "+ boardingPoint + " | "+ reportingTime + " | "+ coachNo + " | "+ tripDate + " | "+ totalFare + " | "+ departureTime + " | "+ destination + " | "+ coachType + " | "+ utkNo );


            float col = 140f;
            float colWidth[] = {col, col, col, col};
            Table table = new Table(colWidth);

            Table table1 = new Table(colWidth);

            Paragraph p = new Paragraph();


            table1.addCell(new Cell(1,4).add(new Paragraph(utkNo).setBold()).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            p.add(table1);

            p.setMarginLeft(30f);
            p.setMarginTop(20f);
            document.add(p);


            table.addCell(new Cell().add(new Paragraph( "Passenger Name :").setBold().setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph( passengerName)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph( "Trip Date :").setBold().setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph( tripDate)).setBorder(Border.NO_BORDER));

            table.addCell(new Cell().add(new Paragraph( "Seats :").setBold().setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph( seats)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph( "Total Fare :").setBold().setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph( totalFare)).setBorder(Border.NO_BORDER));

            table.addCell(new Cell().add(new Paragraph( "Boarding Point :").setBold().setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph( boardingPoint)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph( "Destination :").setBold().setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph( destination)).setBorder(Border.NO_BORDER));

            table.addCell(new Cell().add(new Paragraph( "Reporting Time :").setBold().setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph( reportingTime)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph( "Departure Time :").setBold().setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph( departureTime)).setBorder(Border.NO_BORDER));

            table.addCell(new Cell().add(new Paragraph( "Coach Number :").setBold().setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph( coachNo)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph( "Coach Type :").setBold().setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph( coachType)).setBorder(Border.NO_BORDER));


            Paragraph p1 = new Paragraph();
            p1.add(table);
            p1.setMarginLeft(30f);
            p1.setMarginTop(30f);
            document.add(p1);

            Text footer = new Text("No Mask, No Service");
            Paragraph f = new Paragraph(footer).setFont(PdfFontFactory.createFont(FontConstants.HELVETICA))
                    .setFontSize(15);

            f.setTextAlignment(TextAlignment.CENTER).setMarginTop(45f);
            document.add(f);

            footer = new Text("Developed By MJ Aumi & Saleh Shakib");
            f = new Paragraph(footer).setFont(PdfFontFactory.createFont(FontConstants.HELVETICA))
                    .setFontSize(10);

            f.setTextAlignment(TextAlignment.CENTER).setMarginTop(10f);
            document.add(f);

            document.close();
            System.out.println("pdf Created");
            System.out.println("preparing to send message ...");

            String subject = "User Ticket.";
            String to = userData.getUserEmail();
            String from = "sam404.iums@gmail.com";

            sendMail(subject, to, from);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(passengerName + " | "+seats + " | "+ boardingPoint + " | "+ reportingTime + " | "+ coachNo + " | "+ tripDate + " | "+ totalFare + " | "+ departureTime + " | "+ destination + " | "+ coachType + " | "+ utkNo );
    }


    private void sendMail(String subject, String to, String from) {

        String host = "smtp.gmail.com";

        //get the system properties
        Properties properties = System.getProperties();

        String message = "<html>Hello there, <br> Your Ticket is attached here......<br>Download and preserve this ticket. Thank you for traveling with SUPLUX PARIBAHAN. </html>";


        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sam404.iums@gmail.com", "mustaviCG4.00");
            }

        });


        session.setDebug(true);

        //Step 2 : compose the message [text,multimedia]
        MimeMessage m = new MimeMessage(session);

        try {

            //from email
            m.setFrom(from);

            //adding recipient to message
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //adding subject to message
            m.setSubject(subject);

            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBodyPart = new MimeBodyPart();

            textBodyPart.setContent(message,"text/html");


            MimeBodyPart pdfAttachment = new MimeBodyPart();
            pdfAttachment.attachFile("E:\\CSE 3104\\Project\\SupluxBusTicketManagementSystem\\Generated Ticket\\" + utkNo +".pdf");

            emailContent.addBodyPart(textBodyPart);
            emailContent.addBodyPart(pdfAttachment);
            m.setContent(emailContent);



            Transport.send(m);

            System.out.println("Sent Success.......");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

