package dev.gradienttim.m2.test

import dev.gradienttim.m2.kotlin.dependency
import dev.gradienttim.m2.kotlin.repository
import dev.gradienttim.m2.kotlin.resolveMaven
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class SingleTest : FunSpec({

    test("test notation definition") {
        val repository = repository {
            url("https://repo1.maven.org/")
        }

        val dependency = dependency {
            repository(repository)
            notation("com.google.guava:guava:33.2.1-jre")
        }

        dependency.groupId shouldBe "com.google.guava"
        dependency.artifactId shouldBe "guava"
        dependency.version shouldBe "33.2.1-jre"
    }

    test("validate values from builders") {
        val repository = repository {
            url("https://repo1.maven.org/")
        }

        val dependency = dependency {
            repository(repository)
            groupId("com.google.guava")
            artifactId("guava")
            version("33.2.1-jre")
        }

        repository.url shouldBe "https://repo1.maven.org/"
        repository.id shouldBe "maven2"

        dependency.repository shouldBe repository
        dependency.groupId shouldBe "com.google.guava"
        dependency.artifactId shouldBe "guava"
        dependency.version shouldBe "33.2.1-jre"
        dependency.notation shouldBe "com.google.guava:guava:33.2.1-jre"
    }

    test("receive single dependency from a repository server") {
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

        googleGuavaSource.pom.source shouldNotBe null
        googleGuavaSource.javadoc.source shouldNotBe null
        googleGuavaSource.compiled.source shouldNotBe null

        val module = googleGuavaSource.module.source!!
        module.component.group shouldBe "com.google.guava"
        module.component.module shouldBe "guava"
        module.component.version shouldBe "33.2.1-jre"
    }

})