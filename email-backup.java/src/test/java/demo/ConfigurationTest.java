package demo;

import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class ConfigurationTest {

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String HOST = "host";
    private static final int PORT = 1;
    private static final String EXTENSION1 = "bak";
    private static final String EXTENSION2 = "tmp";
    private static final String FILE1 = "file1";
    private static final String FILE2 = "file2";
    private static final String EMAIL1 = "email1";
    private static final String EMAIL2 = "email2";

    @Test
    public void getParametersTest() {
        String[] args = new String[]{
                "-email=" + EMAIL,
                "-password=" + PASSWORD,
                "-host=" + HOST,
                "-port=" + PORT,
                "-excluded_extensions=" + EXTENSION1 + "," + EXTENSION2,
                "-included_files=" + FILE1 + "," + FILE2,
                "-emails_to=" + EMAIL1 + "," + EMAIL2
        };

        Flags flags = Configuration.parseFlags(args);

        assertNotNull(flags);
        assertEquals(flags.getEmail(), EMAIL);
        assertEquals(flags.getPassword(), PASSWORD);
        assertEquals(flags.getHost(), HOST);
        assertEquals(flags.getPort(), PORT);

        assertNotNull(flags.getExcludedExtensions());
        List<String> skippedExtensions = flags.getExcludedExtensions();
        assertEquals(skippedExtensions.size(), 2);
        assertTrue(skippedExtensions.get(0).equals(EXTENSION1));
        assertTrue(skippedExtensions.get(1).equals(EXTENSION2));

        assertNotNull(flags.getIncludedFiles());
        List<String> includedFiles = flags.getIncludedFiles();
        assertEquals(includedFiles.size(), 2);
        assertTrue(includedFiles.get(0).equals(FILE1));
        assertTrue(includedFiles.get(1).equals(FILE2));

        assertNotNull(flags.getEmailsTo());
        List<String> emailsTo = flags.getEmailsTo();
        assertEquals(emailsTo.size(), 2);
        assertTrue(emailsTo.get(0).equals(EMAIL1));
        assertTrue(emailsTo.get(1).equals(EMAIL2));
    }

    @Test
    public void getParametersMissedEmailTest() {
        String[] args = new String[]{
                "-password=" + PASSWORD,
                "-host=" + HOST,
                "-port=" + PORT
        };

        Flags flags = Configuration.parseFlags(args);

        assertNull(flags);
    }

    @Test
    public void getParametersMissedPasswordTest() {
        String[] args = new String[]{
                "-email=" + EMAIL,
                "-host=" + HOST,
                "-port=" + PORT
        };

        Flags flags = Configuration.parseFlags(args);

        assertNull(flags);
    }

    @Test
    public void getParametersMissedHostTest() {
        String[] args = new String[]{
                "-email=" + EMAIL,
                "-password=" + PASSWORD,
                "-port=" + PORT
        };

        Flags flags = Configuration.parseFlags(args);

        assertNull(flags);
    }

    @Test
    public void getParametersMissedPortTest() {
        String[] args = new String[]{
                "-email=" + EMAIL,
                "-password=" + PASSWORD,
                "-host=" + HOST
        };

        Flags flags = Configuration.parseFlags(args);

        assertNull(flags);
    }
}
