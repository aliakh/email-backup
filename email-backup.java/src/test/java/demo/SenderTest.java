package demo;

import com.icegreen.greenmail.util.DummySSLSocketFactory;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Security;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class SenderTest {

    private static final String EMAIL = "email@email.com";
    private static final String EMAIL2 = "email2@email.com";

    private GreenMail greenMail;

    @BeforeTest
    public void setUp() throws Exception {
        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
        greenMail = new GreenMail(ServerSetupTest.SMTPS);
        greenMail.start();
    }

    @AfterTest
    public void tearDown() throws Exception {
        greenMail.stop();
    }

    @Test
    public void sendTest() throws IOException, MessagingException {
        String fileName = "target" + File.separator + "attachment.bin";

        byte[] content = new byte[]{0, 1};
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(content);
        }

        Flags flags = new Flags(EMAIL, "password", "localhost", ServerSetupTest.SMTPS.getPort(), null, null, new String[]{EMAIL2});
        Sender.send(fileName, flags);

        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertNotNull(messages);
        assertEquals(2, messages.length);

        assertMessage(messages[0], fileName, content);
        assertMessage(messages[1], fileName, content);
    }

    private void assertMessage(MimeMessage message, String fileName, byte[] content) throws IOException, MessagingException {
        assertEquals(message.getSubject(), "Backup " + fileName);

        Address[] sender = message.getFrom();
        assertNotNull(sender);
        assertEquals(sender.length, 1);
        assertEquals(sender[0].toString(), EMAIL);

        Address[] recipients = message.getRecipients(Message.RecipientType.TO);
        assertNotNull(recipients);
        assertEquals(recipients.length, 2);
        assertEquals(recipients[0].toString(), EMAIL);
        assertEquals(recipients[1].toString(), EMAIL2);

        assertNull(message.getRecipients(Message.RecipientType.CC));
        assertNull(message.getRecipients(Message.RecipientType.BCC));

        MimeParser.Result result = MimeParser.parse(message);
        assertEquals(result.getContent(), null);

        Map<String, byte[]> files = result.getFiles();
        assertEquals(files.size(), 1);
        assertTrue(files.containsKey(fileName));

        assertEquals(files.get(fileName), content);
    }
}
