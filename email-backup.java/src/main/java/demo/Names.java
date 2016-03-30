package demo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

final public class Names {

    private static final String DATE_PATTERN = "dd-MM-yyyy HH.mm.ss";

    public static String getCurrentFolderName() throws IOException {
        return new File(".").getCanonicalPath();
    }

    public static String getZipName(String folderName, Date date) {
        return getZipNamePrefix(folderName) + getZipNameSuffix(date);
    }

    static String getZipNamePrefix(String folderName) {
        int i = folderName.lastIndexOf(File.separator);
        return (i < 0) ? folderName : folderName.substring(i + 1);
    }

    static String getZipNameSuffix(Date date) {
        return " [" + new SimpleDateFormat(DATE_PATTERN).format(date) + "].zip";
    }
}
