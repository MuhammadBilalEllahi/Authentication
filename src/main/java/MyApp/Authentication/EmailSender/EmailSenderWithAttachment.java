package MyApp.Authentication.EmailSender;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class EmailSenderWithAttachment {


    private static final String username = "muhammadbilalellahi@gmail.com";
    private static final String password = "bocdwhfaarsbmvhy";
    private static final String host = "smtp.gmail.com";
    ;

    public static void main(String[] args) {

        System.out.println("preparing to send message ...");
        String message = "Hello , Dear, this is message for security check . ";
        String subject = "Confirmation";
        String to = "bilalillahi25@gmail.com";


        EmailSenderWithAttachment.sendAttach("Hello,Konichiwa","Approval","bilalillahi25@gmail.com","");
        EmailSenderWithAttachment.sendEmail("Hello,Konichiwa","Approval","bilalillahi25@gmail.com");

        sendEmail(message,subject,to);
        sendAttach(message,subject,to,"");
    }

    //this is responsible to send the message with attachment
    public static void sendAttach(String message, String subject, String to, String attachment) {

        //Variable for gmail

        //get the system properties
        MimeMessage m = getMimeMessage(host);

        try {

            //from email
            m.setFrom();
            m.setFrom();
            //adding recipient to message
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //adding subject to message
            m.setSubject(subject);
            //attachement..

            //file path

            MimeMultipart mimeMultipart = new MimeMultipart();
            //text
            //file
            MimeBodyPart textMime = new MimeBodyPart();

            MimeBodyPart fileMime = new MimeBodyPart();

            try {

                textMime.setText(message);

                File file = new File(attachment);
                fileMime.attachFile(file);


                mimeMultipart.addBodyPart(textMime);
                mimeMultipart.addBodyPart(fileMime);


            } catch (Exception e) {

                e.printStackTrace();
            }


            m.setContent(mimeMultipart);


            //send

            //Step 3 : send the message using Transport class
            Transport.send(m);


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Sent success...................");
    }

    private static MimeMessage getMimeMessage(String host) {
        Properties properties = System.getProperties();
        //System.out.println("PROPERTIES " + properties);

        //setting important information to properties object

        //host set
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");


        //Step 1: to get the session object..
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }


        });

        session.setDebug(true);

        //Step 2 : compose the message [text,multi media]
        return new MimeMessage(session);
    }

    //this is responsible to send email..
    public static void sendEmail(String message, String subject, String to) {

        //Variable for gmail
        String host="smtp.gmail.com";

        //get the system properties
        MimeMessage m = getMimeMessage(host);

        try {

            //from email
            m.setFrom();

            //adding recipient to message
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //adding subject to message
            m.setSubject(subject);


            //adding text to message
            m.setText(message);

            //send

            //Step 3 : send the message using Transport class
            Transport.send(m);

            System.out.println("Sent success...................");


        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
