package demo

public class Flags(public val email: String, public val password: String, public val host: String, public val port: Int, excludedExtensions: Array<String>?, includedFiles: Array<String>?, emailsTo: Array<String>?) {

    public val excludedExtensions: List<String>
    public val includedFiles: List<String>
    public val emailsTo: List<String>

    init {
        this.excludedExtensions = toList(excludedExtensions)
        this.includedFiles = toList(includedFiles)
        this.emailsTo = toList(emailsTo)
    }

    private fun toList(array: Array<String>?): List<String> {
        return if (array == null) listOf() else listOf(*array)
    }
}
