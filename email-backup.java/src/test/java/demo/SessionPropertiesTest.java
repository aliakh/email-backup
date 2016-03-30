package demo;

import org.testng.annotations.Test;

import java.util.Properties;

import static org.testng.Assert.assertEquals;

public class SessionPropertiesTest {

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String HOST = "host";
    private static final int PORT = 1;

    @Test
    public void getPropertiesTest() {
        Flags flags = new Flags(EMAIL, PASSWORD, HOST, PORT, null, null, null);

        Properties properties = SessionProperties.getProperties(flags);

        assertEquals(properties.getProperty("mail.smtp.user"), EMAIL);
        assertEquals(properties.getProperty("mail.smtp.host"), HOST);
        assertEquals(properties.getProperty("mail.smtp.port"), "" + PORT);
        assertEquals(properties.getProperty("mail.smtp.starttls.enable"), "true");
        assertEquals(properties.getProperty("mail.smtp.auth"), "true");
        assertEquals(properties.getProperty("mail.smtp.debug"), "true");
        assertEquals(properties.getProperty("mail.smtp.socketFactory.port"), "" + PORT);
        assertEquals(properties.getProperty("mail.smtp.socketFactory.class"), "javax.net.ssl.SSLSocketFactory");
        assertEquals(properties.getProperty("mail.smtp.socketFactory.fallback"), "false");
    }
}
