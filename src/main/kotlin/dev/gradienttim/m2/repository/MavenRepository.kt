package dev.gradienttim.m2.repository

class MavenRepository internal constructor(
    val url: String,
    val id: String = "releases",
) {

    fun buildUrl(): String = buildString {
        append(url)
        if (url.endsWith("/").not()) {
            append("/")
        }
        append(id)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is MavenRepository) {
            return (url == other.url) and (id == other.id)
        }
        return false
    }

    override fun hashCode(): Int {
        var result = url.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

    override fun toString(): String = buildString {
        append("MavenRepository{")
        append("id=$id,")
        append("url=$url")
        append("}")
    }

    companion object {
        const val URL_REGEX: String = "(https?)://[\\w.-]+(?:\\.[\\w\\.-]+)+[/\\w\\-.,@?^=%&:;/~+#]*[/\\w\\-@?^=%&;/~+#]?"
        const val ID_REGEX: String = "\\S+"

        @JvmStatic
        fun builder() = RepositoryBuilder()
    }

}