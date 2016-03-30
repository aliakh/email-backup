package demo;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

final public class Zipper {

    final private String zipName;
    final private Flags flags;
    final private String folderName;

    public Zipper(String zipName, Flags flags, String folderName) {
        this.zipName = zipName;
        this.flags = flags;
        this.folderName = folderName;
    }

    public void zip() throws IOException {
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipName))) {
            System.out.println("Archive name: " + zipName);
            File folder = new File(folderName);
            zipFolder(folder, out);
        }

        System.out.println("Archive size: " + new File(zipName).length() + " byte(s)");
    }

    void zipFolder(File folder, ZipOutputStream out) throws IOException {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getAbsolutePath().substring(folderName.length() + 1);

                if (file.isDirectory()) {
                    zipFolder(file, out);
                } else if (exclude(file.getName())) {
                    System.out.println("skip " + name);
                } else {
                    try (FileInputStream in = new FileInputStream(file.getAbsolutePath())) {
                        System.out.println("add  " + name);

                        out.putNextEntry(new ZipEntry(name));
                        ByteStreams.copy(in, out);
                        out.closeEntry();
                    }
                }
            }
        }
    }

    boolean exclude(String fileName) {
        if (fileName.equals(zipName)) {
            return true;
        } else if (flags.getIncludedFiles().contains(fileName)) {
            return false;
        } else {
            int i = fileName.lastIndexOf('.');
            if (i < 0) {
                return false;
            } else {
                String extension = fileName.substring(i + 1);
                return (flags.getExcludedExtensions().contains(extension));
            }
        }
    }
}
