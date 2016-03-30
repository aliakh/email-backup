package demo;

import java.util.Properties;

final public class SessionProperties {

    public static Properties getProperties(Flags flags) {
        Properties properties = new Properties();
        properties.put("mail.smtp.user", flags.getEmail());
        properties.put("mail.smtp.host", flags.getHost());
        properties.put("mail.smtp.port", "" + flags.getPort());
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.debug", "true");
        properties.put("mail.smtp.socketFactory.port", "" + flags.getPort());
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        return properties;
    }
}
