package demo;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class ZipperTest {

    @Test
    public void zipTest() throws IOException {
        final String excludedExtension = "tmp";
        final String separator = File.separator;

        String currentFolderName = new File(".").getCanonicalPath();
        String zipName = currentFolderName + separator + "target" + separator + "folder1.zip";
        Flags flags = new Flags(null, null, null, 0, new String[]{excludedExtension}, null, null);
        String folderName = currentFolderName + separator + "target" + separator + "test-classes" + separator + "folder1";

        new Zipper(zipName, flags, folderName).zip();

        Map<String, byte[]> files = new HashMap<>();
        try (ZipFile file = new ZipFile(zipName)) {
            Enumeration<? extends ZipEntry> entries = file.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                files.put(entry.getName(), ByteStreams.toByteArray(file.getInputStream(entry)));
            }
        }

        for (String fileName : files.keySet()) {
            int i = fileName.lastIndexOf('.');
            assertTrue(i >= 0);
            String extension = fileName.substring(i + 1);
            assertNotEquals(extension, excludedExtension);

            byte[] actualContent = files.get(fileName);
            byte[] expectedContent = Files.toByteArray(new File(folderName + separator + fileName));
            assertEquals(actualContent, expectedContent);
        }
    }

    @Test
    public void excludeByZipNameTest() {
        String fileName = "test.zip";

        assertTrue(new Zipper("test.zip", null, null).exclude(fileName));
    }

    @Test
    public void includeByFileNameTest() {
        String fileName = "test";
        Flags flags = new Flags(null, null, null, 0, null, new String[]{fileName}, null);

        assertFalse(new Zipper(null, flags, null).exclude(fileName));
    }

    @Test
    public void includeTest() {
        String fileName = "test";
        Flags flags = new Flags(null, null, null, 0, null, null, null);

        assertFalse(new Zipper(null, flags, null).exclude(fileName));
    }

    @Test
    public void excludeByExtensionTest() {
        String fileName = "test.ext";
        Flags flags = new Flags(null, null, null, 0, new String[]{"ext"}, null, null);

        assertTrue(new Zipper(null, flags, null).exclude(fileName));
    }
}
