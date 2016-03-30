package demo

import org.testng.Assert.assertEquals
import org.testng.Assert.assertNotNull
import org.testng.annotations.Test

public class FlagsTest {

    private val EMAIL = "email"
    private val PASSWORD = "password"
    private val HOST = "host"
    private val PORT = 465
    private val EXTENSION = "extension"
    private val FILE = "file"
    private val EMAIL2 = "email2"

    @Test
    public fun constructorTest1() {
        val flags = Flags(EMAIL, PASSWORD, HOST, PORT, null, null, null)

        assertEquals(flags.email, EMAIL)
        assertEquals(flags.password, PASSWORD)
        assertEquals(flags.host, HOST)
        assertEquals(flags.port, PORT)

        assertNotNull(flags.excludedExtensions)
        assertEquals(flags.excludedExtensions.size(), 0)

        assertNotNull(flags.includedFiles)
        assertEquals(flags.includedFiles.size(), 0)

        assertNotNull(flags.emailsTo)
        assertEquals(flags.emailsTo.size(), 0)
    }

    @Test
    public fun constructorTest2() {
        val flags = Flags(EMAIL, PASSWORD, HOST, PORT, arrayOf(EXTENSION), arrayOf(FILE), arrayOf(EMAIL2))

        assertEquals(flags.email, EMAIL)
        assertEquals(flags.password, PASSWORD)
        assertEquals(flags.host, HOST)
        assertEquals(flags.port, PORT)

        assertNotNull(flags.excludedExtensions)
        assertEquals(flags.excludedExtensions.size(), 1)
        assertEquals(flags.excludedExtensions.get(0), EXTENSION)

        assertNotNull(flags.includedFiles)
        assertEquals(flags.includedFiles.size(), 1)
        assertEquals(flags.includedFiles.get(0), FILE)

        assertNotNull(flags.emailsTo)
        assertEquals(flags.emailsTo.size(), 1)
        assertEquals(flags.emailsTo.get(0), EMAIL2)
    }
}
