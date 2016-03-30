package demo

import com.google.common.io.ByteStreams
import com.google.common.io.Files
import org.testng.Assert.assertEquals
import org.testng.Assert.assertFalse
import org.testng.Assert.assertNotEquals
import org.testng.Assert.assertTrue
import org.testng.annotations.Test
import java.io.File
import java.io.IOException
import java.util.HashMap
import java.util.zip.ZipFile

public class ZipperTest {

    private val EMAIL = "email"
    private val PASSWORD = "password"
    private val HOST = "host"
    private val PORT = 0

    private val ZIP_NAME = "test.zip"
    private val FOLDER_NAME = "folder"

    @Test
    @Throws(IOException::class)
    public fun zipTest() {
        val excludedExtension = "tmp"
        val separator = File.separator

        val currentFolderName = File(".").canonicalPath
        val zipName = currentFolderName + separator + "target" + separator + "folder1.zip"
        val flags = Flags(EMAIL, PASSWORD, HOST, PORT, arrayOf(excludedExtension), null, null)
        val folderName = currentFolderName + separator + "target" + separator + "test-classes" + separator + "folder1"

        Zipper(zipName, flags, folderName).zip()

        val files = HashMap<String, ByteArray>()
        ZipFile(zipName).use { file ->
            val entries = file.entries()
            while (entries.hasMoreElements()) {
                val entry = entries.nextElement()
                files.put(entry.name, ByteStreams.toByteArray(file.getInputStream(entry)))
            }
        }

        for (fileName in files.keySet()) {
            val i = fileName.lastIndexOf('.')
            assertTrue(i >= 0)
            val extension = fileName.substring(i + 1)
            assertNotEquals(extension, excludedExtension)

            val actualContent = files.get(fileName)
            val expectedContent = Files.toByteArray(File(folderName + separator + fileName))
            assertEquals(actualContent, expectedContent)
        }
    }

    @Test
    public fun excludeByZipNameTest() {
        val fileName = ZIP_NAME
        val flags = Flags(EMAIL, PASSWORD, HOST, PORT, null, null, null)

        assertTrue(Zipper(ZIP_NAME, flags, FOLDER_NAME).exclude(fileName))
    }

    @Test
    public fun includeByFileNameTest() {
        val fileName = "test"
        val flags = Flags(EMAIL, PASSWORD, HOST, PORT, null, arrayOf(fileName), null)

        assertFalse(Zipper(ZIP_NAME, flags, FOLDER_NAME).exclude(fileName))
    }

    @Test
    public fun includeTest() {
        val fileName = "test"
        val flags = Flags(EMAIL, PASSWORD, HOST, PORT, null, null, null)

        assertFalse(Zipper(ZIP_NAME, flags, FOLDER_NAME).exclude(fileName))
    }

    @Test
    public fun excludeByExtensionTest() {
        val fileName = "test.ext"
        val flags = Flags(EMAIL, PASSWORD, HOST, PORT, arrayOf("ext"), null, null)

        assertTrue(Zipper(ZIP_NAME, flags, FOLDER_NAME).exclude(fileName))
    }
}
