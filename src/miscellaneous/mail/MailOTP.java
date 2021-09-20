package miscellaneous.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.Random;

public class MailOTP {

    public MailOTP() {
    }

    public void sendMail(String message, String subject, String to, String from) {

        String host = "smtp.gmail.com";
        //get the system properties
        Properties properties = System.getProperties();

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
            pdfAttachment.attachFile("D:\\javaProject\\generate_pdf\\pdf_test.pdf");

            emailContent.addBodyPart(textBodyPart);
            //emailContent.addBodyPart(pdfAttachment);
            m.setContent(emailContent);



            Transport.send(m);

            System.out.println("Sent Success.......");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getRandomNumberString() {

        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

}


