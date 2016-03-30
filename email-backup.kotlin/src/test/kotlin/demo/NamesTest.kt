package demo

import org.testng.Assert.assertEquals
import org.testng.Assert.assertTrue
import org.testng.annotations.Test
import java.io.File
import java.io.IOException
import java.util.Date

public class NamesTest {

    @Test
    @Throws(IOException::class)
    public fun getFolderNameTest() {
        assertTrue(Names.getCurrentFolderName().endsWith(File.separator + "email-backup.kotlin"))
    }

    @Test
    public fun getZipNameTest() {
        val zipName = Names.getZipName(File.separator + "folder", Date(0))
        val tokens = zipName.split("\\[|\\]")

        assertEquals(tokens.size(), 3)
        assertEquals(tokens[0], "folder ")
        assertEquals(tokens[1], "31-12-1969 21.00.00")
        assertEquals(tokens[2], ".zip")
    }

    @Test
    public fun getFileNamePrefixTest1() {
        val separator = File.separator
        assertEquals(Names.getZipNamePrefix("disc:" + separator), "")
        assertEquals(Names.getZipNamePrefix("disc:" + separator + "folder1"), "folder1")
        assertEquals(Names.getZipNamePrefix("disc:" + separator + "folder1" + separator + "folder2"), "folder2")
    }

    @Test
    public fun getFileNamePrefixTest2() {
        assertEquals(Names.getZipNamePrefix("folder"), "folder")
    }

    @Test
    public fun getFileNameSuffixTest() {
        assertEquals(Names.getZipNameSuffix(Date(0)), " [31-12-1969 21.00.00].zip")
    }
}
