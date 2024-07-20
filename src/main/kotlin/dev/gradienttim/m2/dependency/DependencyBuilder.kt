package dev.gradienttim.m2.dependency

import dev.gradienttim.m2.repository.MavenRepository
import kotlin.jvm.Throws

class DependencyBuilder internal constructor() {

    private lateinit var dependencyRepository: MavenRepository
    private lateinit var dependencyGroupId: String
    private lateinit var dependencyArtifactId: String
    private lateinit var dependencyVersion: String

    fun repository(repository: MavenRepository): DependencyBuilder {
        this.dependencyRepository = repository
        return this
    }

    @Throws(IllegalStateException::class)
    fun groupId(groupId: String): DependencyBuilder {
        if (groupId.matches(Regex(MavenDependency.GROUP_ID_REGEX)).not()) {
            error("$groupId is not a valid groupId pattern")
        }
        this.dependencyGroupId = groupId
        return this
    }

    @Throws(IllegalStateException::class)
    fun artifactId(artifactId: String): DependencyBuilder {
        if (artifactId.matches(Regex(MavenDependency.ARTIFACT_ID_REGEX)).not()) {
            error("$artifactId is not a valid artifactId pattern")
        }
        this.dependencyArtifactId = artifactId
        return this
    }

    @Throws(IllegalStateException::class)
    fun version(version: String): DependencyBuilder {
        if (version.matches(Regex(MavenDependency.VERSION_REGEX)).not()) {
            error("$version is not a valid version pattern")
        }
        this.dependencyVersion = version
        return this
    }

    @Throws(IllegalStateException::class)
    fun notation(notation: String): DependencyBuilder {
        if (notation.matches(Regex(MavenDependency.NOTATION_REGEX)).not()) {
            error("$notation is not a valid notation pattern")
        }
        val parts = notation.split(":")
        if (parts.size != 3) {
            error("notation must have three parts (groupId:artifactId:version)")
        }
        this.dependencyGroupId = parts[0]
        this.dependencyArtifactId = parts[1]
        this.dependencyVersion = parts[2]
        return this
    }

    @Throws(IllegalStateException::class)
    fun build(): MavenDependency {
        if (::dependencyRepository.isInitialized.not()) {
            error("repository must be initialized")
        }
        if (::dependencyGroupId.isInitialized.not()) {
            error("groupId must be initialized")
        }
        if (::dependencyArtifactId.isInitialized.not()) {
            error("artifactId must be initialized")
        }
        if (::dependencyVersion.isInitialized.not()) {
            error("version must be initialized")
        }
        return MavenDependency(
            dependencyRepository,
            dependencyGroupId,
            dependencyArtifactId,
            dependencyVersion,
        )
    }

}