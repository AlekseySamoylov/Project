package sample;

        import java.util.Properties;
        import javax.mail.Message;
        import javax.mail.MessagingException;
        import javax.mail.Session;
        import javax.mail.Transport;
        import javax.mail.internet.AddressException;
        import javax.mail.internet.InternetAddress;
        import javax.mail.internet.MimeMessage;


public class MailSend {

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;


    public void generateAndSendEmail(String key, String value) throws AddressException, MessagingException {


        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("alekseysamoylov89@gmail.com"));
        generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("repaircarcenter1@gmail.com"));
        generateMailMessage.setSubject(key);
        generateMailMessage.setContent(value, "text/html; charset=utf-8");


        Transport transport = getMailSession.getTransport("smtp");

        transport.connect("smtp.gmail.com", StaticValues.id, StaticValues.password);
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}

