package demo;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

final public class SessionAuthenticator extends Authenticator {

    final private String email;
    final private String password;

    public SessionAuthenticator(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(email, password);
    }
}