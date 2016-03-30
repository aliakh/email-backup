package demo

import java.util.Properties

public object SessionProperties {

    public fun getProperties(flags: Flags): Properties {
        val properties = Properties()
        properties.put("mail.smtp.user", flags.email)
        properties.put("mail.smtp.host", flags.host)
        properties.put("mail.smtp.port", "" + flags.port)
        properties.put("mail.smtp.starttls.enable", "true")
        properties.put("mail.smtp.auth", "true")
        properties.put("mail.smtp.debug", "true")
        properties.put("mail.smtp.socketFactory.port", "" + flags.port)
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
        properties.put("mail.smtp.socketFactory.fallback", "false")
        return properties
    }
}
