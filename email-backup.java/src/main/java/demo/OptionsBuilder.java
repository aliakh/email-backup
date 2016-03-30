package demo;

import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

final public class OptionsBuilder {

    @SuppressWarnings("static-access")
    public static Options build() {
        Options options = new Options();

        options.addOption(OptionBuilder
                .withType(String.class)
                .withArgName("email")
                .hasArg()
                .isRequired()
                .withDescription("email sender/recipient")
                .create("email"));

        options.addOption(OptionBuilder
                .withType(String.class)
                .withArgName("password")
                .hasArg()
                .isRequired()
                .withDescription("email password")
                .create("password"));

        options.addOption(OptionBuilder
                .withType(String.class)
                .withArgName("host")
                .hasArg()
                .isRequired()
                .withDescription("SMTP host")
                .create("host"));

        options.addOption(OptionBuilder
                .withType(Number.class)
                .withArgName("port")
                .hasArg()
                .isRequired()
                .withDescription("SMTP port")
                .create("port"));

        options.addOption(OptionBuilder
                .withType(String.class)
                .withArgName("extension1,extension2,...")
                .hasArgs()
                .withValueSeparator(',')
                .isRequired(false)
                .withDescription("excluded file extensions (optional)")
                .create("excluded_extensions"));

        options.addOption(OptionBuilder
                .withType(String.class)
                .withArgName("file1,file2,...")
                .hasArgs()
                .withValueSeparator(',')
                .isRequired(false)
                .withDescription("included files (optional)")
                .create("included_files"));

        options.addOption(OptionBuilder
                .withType(String.class)
                .withArgName("email1,email2,...")
                .hasArgs()
                .withValueSeparator(',')
                .isRequired(false)
                .withDescription("additional recipients (optional)")
                .create("emails_to"));

        return options;
    }
}
