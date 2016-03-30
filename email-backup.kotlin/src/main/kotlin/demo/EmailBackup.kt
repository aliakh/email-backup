package demo

import java.io.IOException
import java.util.Date
import javax.mail.MessagingException

public object EmailBackup {

    @JvmStatic public fun main(args: Array<String>) {
        try {
            val flags = Configuration.parseFlags(*args)
            if (flags != null) {
                val folderName = Names.getCurrentFolderName()
                val zipName = Names.getZipName(folderName, Date())

                val timer1 = Timer("Zipping")
                Zipper(zipName, flags, folderName).zip()
                timer1.finish()

                val timer2 = Timer("Sending")
                Sender.send(zipName, flags)
                timer2.finish()
            }
        } catch (e: IOException) {
            println(e.getMessage())
        } catch (e: MessagingException) {
            println(e.getMessage())
        }
    }
}
