package demo

import org.apache.commons.cli.OptionBuilder
import org.apache.commons.cli.Options

public object OptionsBuilder {

    @SuppressWarnings("static-access")
    public fun build(): Options {
        val options = Options()

        OptionBuilder.withType(String::class.java)
        OptionBuilder.withArgName("email")
        OptionBuilder.hasArg()
        OptionBuilder.isRequired()
        OptionBuilder.withDescription("email sender/recipient")
        options.addOption(OptionBuilder.create("email"))

        OptionBuilder.withType(String::class.java)
        OptionBuilder.withArgName("password")
        OptionBuilder.hasArg()
        OptionBuilder.isRequired()
        OptionBuilder.withDescription("email password")
        options.addOption(OptionBuilder.create("password"))

        OptionBuilder.withType(String::class.java)
        OptionBuilder.withArgName("host")
        OptionBuilder.hasArg()
        OptionBuilder.isRequired()
        OptionBuilder.withDescription("SMTP host")
        options.addOption(OptionBuilder.create("host"))

        OptionBuilder.withType(Number::class.java)
        OptionBuilder.withArgName("port")
        OptionBuilder.hasArg()
        OptionBuilder.isRequired()
        OptionBuilder.withDescription("SMTP port")
        options.addOption(OptionBuilder.create("port"))

        OptionBuilder.withType(String::class.java)
        OptionBuilder.withArgName("extension1,extension2,...")
        OptionBuilder.hasArgs()
        OptionBuilder.withValueSeparator(',')
        OptionBuilder.isRequired(false)
        OptionBuilder.withDescription("excluded file extensions (optional)")
        options.addOption(OptionBuilder.create("excluded_extensions"))

        OptionBuilder.withType(String::class.java)
        OptionBuilder.withArgName("file1,file2,...")
        OptionBuilder.hasArgs()
        OptionBuilder.withValueSeparator(',')
        OptionBuilder.isRequired(false)
        OptionBuilder.withDescription("included files (optional)")
        options.addOption(OptionBuilder.create("included_files"))

        OptionBuilder.withType(String::class.java)
        OptionBuilder.withArgName("email1,email2,...")
        OptionBuilder.hasArgs()
        OptionBuilder.withValueSeparator(',')
        OptionBuilder.isRequired(false)
        OptionBuilder.withDescription("additional recipients (optional)")
        options.addOption(OptionBuilder.create("emails_to"))

        return options
    }
}
