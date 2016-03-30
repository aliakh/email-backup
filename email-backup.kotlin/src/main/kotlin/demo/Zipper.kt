package demo

import com.google.common.io.ByteStreams

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

public class Zipper(private val zipName: String, private val flags: Flags, private val folderName: String) {

    @Throws(IOException::class)
    public fun zip() {
        ZipOutputStream(FileOutputStream(zipName)).use { out ->
            println("Archive name: " + zipName)
            val folder = File(folderName)
            zipFolder(folder, out)
        }

        println("Archive size: " + File(zipName).length() + " byte(s)")
    }

    @Throws(IOException::class)
    fun zipFolder(folder: File, out: ZipOutputStream) {
        val files = folder.listFiles()
        if (files != null) {
            for (file in files) {
                val name = file.absolutePath.substring(folderName.length() + 1)

                if (file.isDirectory) {
                    zipFolder(file, out)
                } else if (exclude(file.name)) {
                    println("skip " + name)
                } else {
                    FileInputStream(file.absolutePath).use { `in` ->
                        println("add  " + name)

                        out.putNextEntry(ZipEntry(name))
                        ByteStreams.copy(`in`, out)
                        out.closeEntry()
                    }
                }
            }
        }
    }

    fun exclude(fileName: String): Boolean {
        if (fileName == zipName) {
            return true
        } else if (flags.includedFiles.contains(fileName)) {
            return false
        } else {
            val i = fileName.lastIndexOf('.')
            if (i < 0) {
                return false
            } else {
                val extension = fileName.substring(i + 1)
                return (flags.excludedExtensions.contains(extension))
            }
        }
    }
}
