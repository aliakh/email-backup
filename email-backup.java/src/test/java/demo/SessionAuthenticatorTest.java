package demo;

import org.testng.annotations.Test;

import javax.mail.PasswordAuthentication;

import static org.testng.Assert.assertEquals;

public class SessionAuthenticatorTest {

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    @Test
    public void constructorTest() {
        SessionAuthenticator authenticator = new SessionAuthenticator(EMAIL, PASSWORD);

        PasswordAuthentication authentication = authenticator.getPasswordAuthentication();

        assertEquals(authentication.getUserName(), EMAIL);
        assertEquals(authentication.getPassword(), PASSWORD);
    }
}
