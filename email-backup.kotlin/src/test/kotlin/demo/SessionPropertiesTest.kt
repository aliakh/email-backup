package demo

import org.testng.Assert.assertEquals
import org.testng.annotations.Test

public class SessionPropertiesTest {

    private val EMAIL = "email"
    private val PASSWORD = "password"
    private val HOST = "host"
    private val PORT = 1

    @Test
    public fun getPropertiesTest() {
        val flags = Flags(EMAIL, PASSWORD, HOST, PORT, null, null, null)

        val properties = SessionProperties.getProperties(flags)

        assertEquals(properties.getProperty("mail.smtp.user"), EMAIL)
        assertEquals(properties.getProperty("mail.smtp.host"), HOST)
        assertEquals(properties.getProperty("mail.smtp.port"), "" + PORT)
        assertEquals(properties.getProperty("mail.smtp.starttls.enable"), "true")
        assertEquals(properties.getProperty("mail.smtp.auth"), "true")
        assertEquals(properties.getProperty("mail.smtp.debug"), "true")
        assertEquals(properties.getProperty("mail.smtp.socketFactory.port"), "" + PORT)
        assertEquals(properties.getProperty("mail.smtp.socketFactory.class"), "javax.net.ssl.SSLSocketFactory")
        assertEquals(properties.getProperty("mail.smtp.socketFactory.fallback"), "false")
    }
}
