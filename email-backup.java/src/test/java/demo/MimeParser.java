package demo;

import com.google.common.io.ByteStreams;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MimeParser {

    public static Result parse(MimeMessage message) throws IOException, MessagingException {
        return new Parser().parse(message);
    }

    static class Result {

        private final String content;
        private final Map<String, byte[]> files;

        public Result(String content, Map<String, byte[]> files) {
            this.content = content;
            this.files = files;
        }

        public String getContent() {
            return content;
        }

        public Map<String, byte[]> getFiles() {
            return files;
        }
    }

    private static class Parser {

        private String content;
        private Map<String, byte[]> files = new HashMap<>();

        private Result parse(Message message) throws IOException, MessagingException {
            String contentType = message.getContentType();

            if (contentType.contains("multipart")) {
                Multipart multipart = (Multipart) message.getContent();
                for (int i = 0; i < multipart.getCount(); i++) {
                    MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(i);
                    if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                        files.put(part.getFileName(), ByteStreams.toByteArray(part.getInputStream()));
                    } else {
                        content = part.getContent().toString();
                    }
                }
            } else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                Object content = message.getContent();
                if (content != null) {
                    this.content = content.toString();
                }
            }

            return new Result(content, files);
        }
    }
}