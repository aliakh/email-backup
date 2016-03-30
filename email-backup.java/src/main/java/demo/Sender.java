package demo;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

final public class Sender {

    public static void send(String zipName, Flags flags) throws MessagingException {
        Transport.send(getMessage(zipName, flags));
    }

    private static MimeMessage getMessage(String zipName, Flags flags) throws MessagingException {
        MimeMessage message = new MimeMessage(getSession(flags));
        message.setFrom(new InternetAddress(flags.getEmail()));

        List<String> emailsTo = getEmailsTo(flags);
        for (String emailTo : emailsTo) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
        }

        message.setSubject("Backup " + zipName);
        message.setContent(getContent(zipName));
        return message;
    }

    private static Session getSession(Flags flags) {
        Authenticator authenticator = new SessionAuthenticator(flags.getEmail(), flags.getPassword());
        Properties properties = SessionProperties.getProperties(flags);
        return Session.getInstance(properties, authenticator);
    }

    private static List<String> getEmailsTo(Flags flags) {
        List<String> emailsTo = new ArrayList<>();
        emailsTo.add(flags.getEmail());
        emailsTo.addAll(flags.getEmailsTo());
        return emailsTo;
    }

    private static Multipart getContent(String zipName) throws MessagingException {
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(getMessageBody(zipName));
        return multipart;
    }

    private static MimeBodyPart getMessageBody(String zipName) throws MessagingException {
        MimeBodyPart messageBody = new MimeBodyPart();
        messageBody.setText("");
        DataSource source = new FileDataSource(zipName);
        messageBody.setDataHandler(new DataHandler(source));
        messageBody.setFileName(zipName);
        return messageBody;
    }
}