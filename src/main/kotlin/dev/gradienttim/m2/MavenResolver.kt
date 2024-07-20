package dev.gradienttim.m2

import dev.gradienttim.m2.dependency.DependencyRequest
import dev.gradienttim.m2.dependency.MavenDependency
import dev.gradienttim.m2.dependency.source.DependencySource
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import kotlinx.coroutines.runBlocking
import java.net.Proxy

class MavenResolver internal constructor(
    private val dependencies: List<MavenDependency>,
    private val resolverProxy: Proxy?,
) {

    val httpClient = HttpClient(OkHttp) {
        engine {
            proxy = resolverProxy
        }
    }

    fun result(): ResolverResult {
        val responses: MutableMap<MavenDependency, DependencySource> = mutableMapOf()
        val startTime = System.currentTimeMillis()
        val resolver = this
        runBlocking {
            dependencies.forEach {
                val request = DependencyRequest(resolver, it)
                responses[it] = request.source()
            }
        }
        return ResolverResult(startTime - System.currentTimeMillis(), responses)
    }

    companion object {

        @JvmStatic
        fun resolve(
            dependencies: List<MavenDependency>,
            proxy: Proxy? = null,
        ) = MavenResolver(dependencies, proxy).result()

        @JvmStatic
        fun resolve(
            vararg dependencies: MavenDependency,
            proxy: Proxy? = null,
        ) = resolve(dependencies.toList(), proxy)

    }

}