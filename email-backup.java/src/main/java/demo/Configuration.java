package demo;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

final public class Configuration {

    public static Flags parseFlags(String... args) {
        Options options = OptionsBuilder.build();

        try {
            CommandLine line = new GnuParser().parse(options, args);

            String email = (String) line.getParsedOptionValue("email");
            String password = (String) line.getParsedOptionValue("password");
            String host = (String) line.getParsedOptionValue("host");
            int port = ((Number) line.getParsedOptionValue("port")).intValue();
            String[] excludedExtensions = line.getOptionValues("excluded_extensions");
            String[] includedFiles = line.getOptionValues("included_files");
            String[] emailsTo = line.getOptionValues("emails_to");

            return new Flags(email, password, host, port, excludedExtensions, includedFiles, emailsTo);
        } catch (ParseException e) {
            System.out.println(e.getMessage());

            new HelpFormatter().printHelp("java -jar email-backup.jar", options);
            return null;
        }
    }
}
