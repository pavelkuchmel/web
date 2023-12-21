package main.org.example.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class EmailSender1 {

    final static String FROM = "pavel.koutchmel@gmail.com";
    final static String PASSWORD = "izio lpvr jbff gzjq";
    private final static Properties PROPERTIES = new Properties();

    static {
        PROPERTIES.put("mail.smtp.host", "smtp.gmail.com");
        PROPERTIES.put("mail.smtp.port", "587");
        PROPERTIES.put("mail.smtp.auth", "true");
        PROPERTIES.put("mail.smtp.starttls.enable", "true"); //TLS
    }

    private static Session getSession(){
        return Session.getInstance(PROPERTIES, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASSWORD);
            }
        });
    }

    public static void send(final String to, final String subject, final String message){
        send(to, subject, message, null);
    }

    public static void send(final String to, final String subject, final String message, final String filePath){
        HashSet<String> toSet = new HashSet<>();
        toSet.add(to);

        send(toSet, null, null,  subject, message, null);
    }

    public static void send(final Set<String> to, final Set<String> cc, final Set<String>
                            bcc, final String subject, final String msg, final String attachmentPathname){
        try {

            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(String.join(", ", to))
            );

            if (cc != null && cc.size() > 0){
                message.setRecipients(
                        Message.RecipientType.CC,
                        InternetAddress.parse(String.join(", ", cc))
                );
            }

            if (bcc != null && bcc.size() > 0){
                message.setRecipients(
                        Message.RecipientType.BCC,
                        InternetAddress.parse(String.join(", ", bcc))
                );
            }

            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(msg, "text/html");
            //messageBodyPart.setText(msg);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            if (attachmentPathname != null){
                attachmentPart.attachFile(new File(attachmentPathname));
            }

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (attachmentPathname != null){
                multipart.addBodyPart(attachmentPart);
            }

            //message.setContent(multipart, "text/html");

            //message.setText(msg + " \n Date: " + new Date());
            //message.setContent(msg, "text/html");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[]args) {
        String to = "zarkonmen@gmail.com";
        String subject = "No Spam";
        String message = "No spam. Is test.";

        send(to, subject, message);
    }
}