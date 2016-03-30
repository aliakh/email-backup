package demo

import org.apache.commons.cli.GnuParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.ParseException

public object Configuration {

    public fun parseFlags(vararg args: String): Flags? {
        val options = OptionsBuilder.build()

        try {
            val line = GnuParser().parse(options, args)

            val email = line.getParsedOptionValue("email") as String
            val password = line.getParsedOptionValue("password") as String
            val host = line.getParsedOptionValue("host") as String
            val port = (line.getParsedOptionValue("port") as Number).toInt()
            val excludedExtensions = line.getOptionValues("excluded_extensions")
            val includedFiles = line.getOptionValues("included_files")
            val emailsTo = line.getOptionValues("emails_to")

            return Flags(email, password, host, port, excludedExtensions, includedFiles, emailsTo)
        } catch (e: ParseException) {
            println(e.getMessage())

            HelpFormatter().printHelp("java -jar email-backup.jar", options)
            return null
        }
    }
}
