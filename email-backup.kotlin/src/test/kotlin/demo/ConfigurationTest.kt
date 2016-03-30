package demo

import org.testng.Assert.assertEquals
import org.testng.Assert.assertNotNull
import org.testng.Assert.assertNull
import org.testng.Assert.assertTrue
import org.testng.annotations.Test

public class ConfigurationTest {

    private val EMAIL = "email"
    private val PASSWORD = "password"
    private val HOST = "host"
    private val PORT = 1
    private val EXTENSION1 = "bak"
    private val EXTENSION2 = "tmp"
    private val FILE1 = "file1"
    private val FILE2 = "file2"
    private val EMAIL1 = "email1"
    private val EMAIL2 = "email2"

    @Test
    public fun getParametersTest() {
        val args = arrayOf(
                "-email=" + EMAIL,
                "-password=" + PASSWORD,
                "-host=" + HOST,
                "-port=" + PORT,
                "-excluded_extensions=$EXTENSION1,$EXTENSION2",
                "-included_files=$FILE1,$FILE2",
                "-emails_to=$EMAIL1,$EMAIL2"
        )

        val flags = Configuration.parseFlags(*args)

        assertNotNull(flags)

        if (flags != null) {
            assertEquals(flags.email, EMAIL)
            assertEquals(flags.password, PASSWORD)
            assertEquals(flags.host, HOST)
            assertEquals(flags.port, PORT)

            assertNotNull(flags.excludedExtensions)
            val skippedExtensions = flags.excludedExtensions
            assertEquals(skippedExtensions.size(), 2)
            assertTrue(skippedExtensions.get(0) == EXTENSION1)
            assertTrue(skippedExtensions.get(1) == EXTENSION2)

            assertNotNull(flags.includedFiles)
            val includedFiles = flags.includedFiles
            assertEquals(includedFiles.size(), 2)
            assertTrue(includedFiles.get(0) == FILE1)
            assertTrue(includedFiles.get(1) == FILE2)

            assertNotNull(flags.emailsTo)
            val emailsTo = flags.emailsTo
            assertEquals(emailsTo.size(), 2)
            assertTrue(emailsTo.get(0) == EMAIL1)
            assertTrue(emailsTo.get(1) == EMAIL2)
        }
    }

    @Test
    public fun getParametersMissedEmailTest() {
        val args = arrayOf(
                "-password=" + PASSWORD,
                "-host=" + HOST,
                "-port=" + PORT
        )

        val flags = Configuration.parseFlags(*args)

        assertNull(flags)
    }

    @Test
    public fun getParametersMissedPasswordTest() {
        val args = arrayOf(
                "-email=" + EMAIL,
                "-host=" + HOST,
                "-port=" + PORT
        )

        val flags = Configuration.parseFlags(*args)

        assertNull(flags)
    }

    @Test
    public fun getParametersMissedHostTest() {
        val args = arrayOf(
                "-email=" + EMAIL,
                "-password=" + PASSWORD,
                "-port=" + PORT
        )

        val flags = Configuration.parseFlags(*args)

        assertNull(flags)
    }

    @Test
    public fun getParametersMissedPortTest() {
        val args = arrayOf(
                "-email=" + EMAIL,
                "-password=" + PASSWORD,
                "-host=" + HOST
        )

        val flags = Configuration.parseFlags(*args)

        assertNull(flags)
    }
}
