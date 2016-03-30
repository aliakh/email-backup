package demo;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class FlagsTest {

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String HOST = "host";
    private static final int PORT = 465;
    private static final String EXTENSION = "extension";
    private static final String FILE = "file";
    private static final String EMAIL2 = "email2";

    @Test
    public void constructorTest1() {
        Flags flags = new Flags(EMAIL, PASSWORD, HOST, PORT, null, null, null);

        assertEquals(flags.getEmail(), EMAIL);
        assertEquals(flags.getPassword(), PASSWORD);
        assertEquals(flags.getHost(), HOST);
        assertEquals(flags.getPort(), PORT);

        assertNotNull(flags.getExcludedExtensions());
        assertEquals(flags.getExcludedExtensions().size(), 0);

        assertNotNull(flags.getIncludedFiles());
        assertEquals(flags.getIncludedFiles().size(), 0);

        assertNotNull(flags.getEmailsTo());
        assertEquals(flags.getEmailsTo().size(), 0);
    }

    @Test
    public void constructorTest2() {
        Flags flags = new Flags(EMAIL, PASSWORD, HOST, PORT, new String[]{EXTENSION}, new String[]{FILE}, new String[]{EMAIL2});

        assertEquals(flags.getEmail(), EMAIL);
        assertEquals(flags.getPassword(), PASSWORD);
        assertEquals(flags.getHost(), HOST);
        assertEquals(flags.getPort(), PORT);

        assertNotNull(flags.getExcludedExtensions());
        assertEquals(flags.getExcludedExtensions().size(), 1);
        assertEquals(flags.getExcludedExtensions().get(0), EXTENSION);

        assertNotNull(flags.getIncludedFiles());
        assertEquals(flags.getIncludedFiles().size(), 1);
        assertEquals(flags.getIncludedFiles().get(0), FILE);

        assertNotNull(flags.getEmailsTo());
        assertEquals(flags.getEmailsTo().size(), 1);
        assertEquals(flags.getEmailsTo().get(0), EMAIL2);
    }
}
