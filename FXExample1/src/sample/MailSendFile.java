package sample;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
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


        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");


        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("alekseysamoylov89@gmail.com"));
        generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("repaircarcenter1@gmail.com"));
        generateMailMessage.setSubject("База данных сервиса");


        BodyPart messageAttach = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageAttach);

        DataSource data = new FileDataSource(filename);
        messageAttach.setDataHandler(new DataHandler(data));

        messageAttach.setFileName(filename);

        multipart.addBodyPart(messageAttach);

        generateMailMessage.setContent(multipart);

        Transport transport = getMailSession.getTransport("smtp");

        transport.connect("smtp.gmail.com", StaticValues.id, StaticValues.password);
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}
