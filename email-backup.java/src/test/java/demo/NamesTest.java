package demo;

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NamesTest {

    @Test
    public void getFolderNameTest() throws IOException {
        assertTrue(Names.getCurrentFolderName().endsWith(File.separator + "email-backup.java"));
    }

    @Test
    public void getZipNameTest() {
        String zipName = Names.getZipName(File.separator + "folder", new Date(0));
        String[] tokens = zipName.split("\\[|\\]");

        assertEquals(tokens.length, 3);
        assertEquals(tokens[0], "folder ");
        assertEquals(tokens[1], "31-12-1969 21.00.00");
        assertEquals(tokens[2], ".zip");
    }

    @Test
    public void getFileNamePrefixTest1() {
        final String separator = File.separator;
        assertEquals(Names.getZipNamePrefix("disc:" + separator), "");
        assertEquals(Names.getZipNamePrefix("disc:" + separator + "folder1"), "folder1");
        assertEquals(Names.getZipNamePrefix("disc:" + separator + "folder1" + separator + "folder2"), "folder2");
    }

    @Test
    public void getFileNamePrefixTest2() {
        assertEquals(Names.getZipNamePrefix("folder"), "folder");
    }

    @Test
    public void getFileNameSuffixTest() {
        assertEquals(Names.getZipNameSuffix(new Date(0)), " [31-12-1969 21.00.00].zip");
    }
}
