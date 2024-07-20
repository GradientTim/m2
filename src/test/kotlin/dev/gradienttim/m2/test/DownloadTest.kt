package dev.gradienttim.m2.test

import dev.gradienttim.m2.kotlin.dependency
import dev.gradienttim.m2.kotlin.repository
import dev.gradienttim.m2.kotlin.resolveMaven
import dev.gradienttim.m2.kotlin.toFile
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.io.File

class DownloadTest : FunSpec({

    val file = File("./google-guava.jar")

    test("receive and download a dependency") {
        val repository = repository {
            url("https://repo1.maven.org/")
        }

        val dependency = dependency {
            repository(repository)
            groupId("com.google.guava")
            artifactId("guava")
            version("33.2.1-jre")
        }

        val result = resolveMaven(dependency)
        val googleGuavaSource = result[dependency]

        val compiledSource = googleGuavaSource.compiled.source!!
        compiledSource.toFile(file)

        file.exists() shouldBe true
        file.readBytes().size shouldNotBe 0
    }

    afterTest {
        file.delete()
    }

})