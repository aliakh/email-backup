package demo

import org.testng.Assert.assertEquals
import org.testng.annotations.Test

public class SessionAuthenticatorTest {

    private val EMAIL = "email"
    private val PASSWORD = "password"

    @Test
    public fun constructorTest() {
        val authenticator = SessionAuthenticator(EMAIL, PASSWORD)

        val authentication = authenticator.getPasswordAuthentication()

        assertEquals(authentication.userName, EMAIL)
        assertEquals(authentication.password, PASSWORD)
    }
}
