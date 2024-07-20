package dev.gradienttim.m2.dependency

import dev.gradienttim.m2.dependency.source.SourceType
import dev.gradienttim.m2.repository.MavenRepository

class MavenDependency internal constructor(
    val repository: MavenRepository,
    val groupId: String,
    val artifactId: String,
    val version: String,
) {

    val notation: String = "$groupId:$artifactId:$version"

    fun buildUrl(type: SourceType, addition: String = ""): String = buildString {
        append(repository.buildUrl()).append("/")
        append(groupId.replace(".", "/")).append("/")
        append(artifactId).append("/")
        append(version).append("/")
        append("$artifactId-$version${type.extension}")
        if (addition.isNotEmpty()) {
            append(addition)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is MavenDependency) {
            return (repository == other.repository) and
                    (groupId == other.groupId) and
                    (artifactId == other.artifactId) and
                    (version == other.version)
        }
        return false
    }

    override fun hashCode(): Int {
        var result = repository.hashCode()
        result = 31 * result + groupId.hashCode()
        result = 31 * result + artifactId.hashCode()
        result = 31 * result + version.hashCode()
        return result
    }

    override fun toString(): String = buildString {
        append("MavenDependency{")
        append("repository=$repository,")
        append("groupId=$groupId,")
        append("artifactId=$artifactId,")
        append("version=$version")
        append("}")
    }

    companion object {
        const val GROUP_ID_REGEX: String = "\\S+"
        const val ARTIFACT_ID_REGEX: String = "\\S+"
        const val VERSION_REGEX: String = "\\S+"
        const val NOTATION_REGEX: String = "$GROUP_ID_REGEX:$ARTIFACT_ID_REGEX:$VERSION_REGEX"

        @JvmStatic
        fun builder() = DependencyBuilder()
    }

}