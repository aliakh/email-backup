package demo

import java.util.ArrayList
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Multipart
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

public object Sender {

    @Throws(MessagingException::class)
    public fun send(zipName: String, flags: Flags) {
        Transport.send(getMessage(zipName, flags))
    }

    @Throws(MessagingException::class)
    private fun getMessage(zipName: String, flags: Flags): MimeMessage {
        val message = MimeMessage(getSession(flags))
        message.setFrom(InternetAddress(flags.email))

        val emailsTo = getEmailsTo(flags)
        for (emailTo in emailsTo) {
            message.addRecipient(Message.RecipientType.TO, InternetAddress(emailTo))
        }

        message.subject = "Backup " + zipName
        message.setContent(getContent(zipName))
        return message
    }

    private fun getSession(flags: Flags): Session {
        val authenticator = SessionAuthenticator(flags.email, flags.password)
        val properties = SessionProperties.getProperties(flags)
        return Session.getInstance(properties, authenticator)
    }

    private fun getEmailsTo(flags: Flags): List<String> {
        val emailsTo = ArrayList<String>()
        emailsTo.add(flags.email)
        emailsTo.addAll(flags.emailsTo)
        return emailsTo
    }

    @Throws(MessagingException::class)
    private fun getContent(zipName: String): Multipart {
        val multipart = MimeMultipart()
        multipart.addBodyPart(getMessageBody(zipName))
        return multipart
    }

    @Throws(MessagingException::class)
    private fun getMessageBody(zipName: String): MimeBodyPart {
        val messageBody = MimeBodyPart()
        messageBody.setText("")
        val source = FileDataSource(zipName)
        messageBody.dataHandler = DataHandler(source)
        messageBody.fileName = zipName
        return messageBody
    }
}