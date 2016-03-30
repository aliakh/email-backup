package demo

import com.icegreen.greenmail.util.DummySSLSocketFactory
import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.ServerSetupTest
import org.testng.Assert.assertEquals
import org.testng.Assert.assertNotNull
import org.testng.Assert.assertNull
import org.testng.Assert.assertTrue
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.Security
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.internet.MimeMessage

public class SenderTest {

    private val EMAIL = "email@email.com"
    private val EMAIL2 = "email2@email.com"

    private var greenMail: GreenMail? = null

    @BeforeTest
    @Throws(Exception::class)
    public fun setUp() {
        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory::class.java.getName())
        greenMail = GreenMail(ServerSetupTest.SMTPS)
        greenMail!!.start()
    }

    @AfterTest
    @Throws(Exception::class)
    public fun tearDown() {
        greenMail!!.stop()
    }

    @Test
    @Throws(IOException::class, MessagingException::class)
    public fun sendTest() {
        val fileName = "target" + File.separator + "attachment.bin"

        val content = byteArrayOf(0, 1)
        FileOutputStream(fileName).use { fos -> fos.write(content) }

        val flags = Flags(EMAIL, "password", "localhost", ServerSetupTest.SMTPS.getPort(), null, null, arrayOf(EMAIL2))
        Sender.send(fileName, flags)

        val messages = greenMail!!.getReceivedMessages()
        assertNotNull(messages)
        assertEquals(2, messages.size())

        assertMessage(messages[0], fileName, content)
        assertMessage(messages[1], fileName, content)
    }

    @Throws(IOException::class, MessagingException::class)
    private fun assertMessage(message: MimeMessage, fileName: String, content: ByteArray) {
        assertEquals(message.subject, "Backup " + fileName)

        val sender = message.from
        assertNotNull(sender)
        assertEquals(sender.size(), 1)
        assertEquals(sender[0].toString(), EMAIL)

        val recipients = message.getRecipients(Message.RecipientType.TO)
        assertNotNull(recipients)
        assertEquals(recipients.size(), 2)
        assertEquals(recipients[0].toString(), EMAIL)
        assertEquals(recipients[1].toString(), EMAIL2)

        assertNull(message.getRecipients(Message.RecipientType.CC))
        assertNull(message.getRecipients(Message.RecipientType.BCC))

        val result = MimeParser.parse(message)
        assertEquals(result.content, null)

        val files = result.files
        assertEquals(files.size(), 1)
        assertTrue(files.containsKey(fileName))

        assertEquals(files.get(fileName), content)
    }
}
