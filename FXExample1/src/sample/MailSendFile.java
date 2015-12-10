package sample;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

/**
 * Created by AlekseiSamoilov on 10/12/15.
 */
public class MailSendFile {
    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;


    MailSendFile() throws AddressException, MessagingException {
        String filename = "works.ser";
        //  System.out.println("\n 1st ===> setup Mail Server Properties..");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        // System.out.println("Mail Server Properties have been setup successfully..");

        // Step2
        //System.out.println("\n\n 2nd ===> get Mail Session..");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("alekseysamoylov89@gmail.com"));
        generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("repaircarcenter1@gmail.com"));
        generateMailMessage.setSubject("База данных сервиса");
       // generateMailMessage.setContent(value, "text/html; charset=utf-8");
        BodyPart messageAttach = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageAttach);

        DataSource data = new FileDataSource(filename);
        messageAttach.setDataHandler(new DataHandler(data));

        messageAttach.setFileName(filename);

        multipart.addBodyPart(messageAttach);

        generateMailMessage.setContent(multipart);
        //System.out.println("Mail Session has been created successfully..");

        // Step3
        //System.out.println("\n\n 3rd ===> Get Session and Send mail");
        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com", "repaircarcenter", "***");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}
