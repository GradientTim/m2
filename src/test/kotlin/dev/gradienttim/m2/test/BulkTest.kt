package dev.gradienttim.m2.test

import dev.gradienttim.m2.dependency.MavenDependency
import dev.gradienttim.m2.kotlin.dependency
import dev.gradienttim.m2.kotlin.repository
import dev.gradienttim.m2.kotlin.resolveMaven
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldNotBe

class BulkTest : FunSpec({

    test("receive bulk dependencies from a repository server") {
        val repository = repository {
            url("https://repo1.maven.org/")
        }

        val dependencies = mutableListOf<MavenDependency>()
        mapOf(
            "kotlin-archetype-js" to "1.9.25",
            "kotlin-project-model" to "1.9.25",
            "kotlin-daemon" to "2.0.20-Beta2",
            "kotlin-daemon-client" to "2.0.20-Beta2",
            "kotlin-compiler-runner" to "2.0.20-Beta2",
        ).forEach { (artifact, version) ->
            dependencies.add(dependency {
                repository(repository)
                groupId("org.jetbrains.kotlin")
                artifactId(artifact)
                version(version)
            })
        }

        val result = resolveMaven(dependencies)
        val kotlinDaemonResult = result[2]

        kotlinDaemonResult.source.source shouldNotBe null
    }

})