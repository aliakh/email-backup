package demo

import javax.mail.Authenticator
import javax.mail.PasswordAuthentication

public class SessionAuthenticator(private val email: String, private val password: String) : Authenticator() {

    public override fun getPasswordAuthentication(): PasswordAuthentication {
        return PasswordAuthentication(email, password)
    }
}