package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final public class Flags {

    final private String email;
    final private String password;
    final private String host;
    final private int port;
    final private List<String> excludedExtensions;
    final private List<String> includedFiles;
    final private List<String> emailsTo;

    public Flags(String email, String password, String host, int port, String[] excludedExtensions, String[] includedFiles, String[] emailsTo) {
        this.email = email;
        this.password = password;
        this.host = host;
        this.port = port;
        this.excludedExtensions = toList(excludedExtensions);
        this.includedFiles = toList(includedFiles);
        this.emailsTo = toList(emailsTo);
    }

    private List<String> toList(String[] array) {
        List<String> list = new ArrayList<>();
        if (array != null) {
            list.addAll(Arrays.asList(array));
        }
        return list;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public List<String> getExcludedExtensions() {
        return excludedExtensions;
    }

    public List<String> getIncludedFiles() {
        return includedFiles;
    }

    public List<String> getEmailsTo() {
        return emailsTo;
    }
}
