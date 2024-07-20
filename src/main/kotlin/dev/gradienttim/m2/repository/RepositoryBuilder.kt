package dev.gradienttim.m2.repository

import kotlin.jvm.Throws

class RepositoryBuilder internal constructor() {

    private lateinit var repositoryUrl: String
    private var repositoryId: String = "maven2"

    @Throws(IllegalStateException::class)
    fun url(url: String): RepositoryBuilder {
        if (url.matches(Regex(MavenRepository.URL_REGEX)).not()) {
            error("$url is not a valid url pattern")
        }
        this.repositoryUrl = url
        return this
    }

    @Throws(IllegalStateException::class)
    fun id(id: String): RepositoryBuilder {
        if (id.matches(Regex(MavenRepository.ID_REGEX)).not()) {
            error("$id is not a valid id pattern")
        }
        this.repositoryId = id
        return this
    }

    @Throws(IllegalStateException::class)
    fun build(): MavenRepository {
        if (::repositoryUrl.isInitialized.not()) {
            error("url must be initialized")
        }
        return MavenRepository(repositoryUrl, repositoryId)
    }

}