package dev.gradienttim.m2.dependency

import dev.gradienttim.m2.MavenResolver
import dev.gradienttim.m2.dependency.source.DependencySource
import dev.gradienttim.m2.dependency.source.SourceSection
import dev.gradienttim.m2.dependency.source.SourceType
import dev.gradienttim.m2.models.ArtifactModule
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import okio.Source
import okio.buffer
import okio.source

internal class DependencyRequest(
    private val resolver: MavenResolver,
    val dependency: MavenDependency,
) {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun source(): DependencySource {
        var artifactModule: ArtifactModule? = null
        response(dependency.buildUrl(SourceType.MODULE))?.let {
            val inputStream = it.buffer().inputStream()
            artifactModule = Json.decodeFromStream(inputStream)
        }
        return DependencySource(
            SourceSection(
                response(dependency.buildUrl(SourceType.COMPILED)),
                response(dependency.buildUrl(SourceType.COMPILED, ".asc")),
                response(dependency.buildUrl(SourceType.COMPILED, ".md5")),
                response(dependency.buildUrl(SourceType.COMPILED, ".sha1")),
            ),
            SourceSection(
                response(dependency.buildUrl(SourceType.SOURCE)),
                response(dependency.buildUrl(SourceType.SOURCE, ".asc")),
                response(dependency.buildUrl(SourceType.SOURCE, ".md5")),
                response(dependency.buildUrl(SourceType.SOURCE, ".sha1")),
            ),
            SourceSection(
                response(dependency.buildUrl(SourceType.JAVADOC)),
                response(dependency.buildUrl(SourceType.JAVADOC, ".asc")),
                response(dependency.buildUrl(SourceType.JAVADOC, ".md5")),
                response(dependency.buildUrl(SourceType.JAVADOC, ".sha1")),
            ),
            SourceSection(
                artifactModule,
                response(dependency.buildUrl(SourceType.MODULE, ".asc")),
                response(dependency.buildUrl(SourceType.MODULE, ".md5")),
                response(dependency.buildUrl(SourceType.MODULE, ".sha1")),
            ),
            SourceSection(
                response(dependency.buildUrl(SourceType.POM)),
                response(dependency.buildUrl(SourceType.POM, ".asc")),
                response(dependency.buildUrl(SourceType.POM, ".md5")),
                response(dependency.buildUrl(SourceType.POM, ".sha1")),
            ),
        )
    }

    @OptIn(InternalAPI::class)
    private suspend fun response(url: String): Source? {
        val response = resolver.httpClient.get(Url(url))
        val status = response.status.value
        if (status in 200..299) {
            return response.content.toInputStream().source()
        }
        return null
    }

}