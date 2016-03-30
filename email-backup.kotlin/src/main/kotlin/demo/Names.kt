package demo

import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

public object Names {

    private val DATE_PATTERN = "dd-MM-yyyy HH.mm.ss"

    @Throws(IOException::class)
    public fun getCurrentFolderName(): String {
        return File(".").canonicalPath
    }

    public fun getZipName(folderName: String, date: Date): String {
        return getZipNamePrefix(folderName) + getZipNameSuffix(date)
    }

    fun getZipNamePrefix(folderName: String): String {
        val i = folderName.lastIndexOf(File.separator)
        return if ((i < 0)) folderName else folderName.substring(i + 1)
    }

    fun getZipNameSuffix(date: Date): String {
        return " [" + SimpleDateFormat(DATE_PATTERN).format(date) + "].zip"
    }
}
