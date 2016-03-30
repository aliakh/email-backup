package demo

import com.google.common.io.ByteStreams
import java.io.IOException
import java.util.HashMap
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Multipart
import javax.mail.Part
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage

public object MimeParser {

    @Throws(IOException::class, MessagingException::class)
    public fun parse(message: MimeMessage): Result {
        return Parser().parse(message)
    }

    class Result(public val content: String?, public val files: Map<String, ByteArray>)

    private class Parser {

        private var content: String? = null
        private val files = HashMap<String, ByteArray>()

        @Throws(IOException::class, MessagingException::class)
        fun parse(message: Message): Result {
            val contentType = message.contentType

            if (contentType.contains("multipart")) {
                val multipart = message.content as Multipart
                for (i in 0..multipart.count - 1) {
                    val part = multipart.getBodyPart(i) as MimeBodyPart
                    if (Part.ATTACHMENT.equals(part.disposition, ignoreCase = true)) {
                        files.put(part.fileName, ByteStreams.toByteArray(part.inputStream))
                    } else {
                        content = part.content.toString()
                    }
                }
            } else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                val content = message.content
                if (content != null) {
                    this.content = content.toString()
                }
            }

            return Result(content, files)
        }
    }
}