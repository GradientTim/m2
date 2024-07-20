import com.vanniktech.maven.publish.SonatypeHost

plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("com.vanniktech.maven.publish") version "0.28.0"
}

group = property("project.group")!!
version = property("project.version")!!

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor", "ktor-client-core", "2.3.12")
    implementation("io.ktor", "ktor-client-okhttp", "2.3.12")
    implementation("org.jetbrains.kotlinx", "kotlinx-serialization-json", "1.7.1")

    testImplementation("io.kotest", "kotest-runner-junit5", "5.8.1")
    testImplementation("io.kotest", "kotest-assertions-core", "5.8.1")
}

kotlin {
    jvmToolchain(21)
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates(project.group.toString(), project.name, project.version.toString())

    pom {
        url.set("https://github.com/GradientTim/m2")
        inceptionYear.set("2024")
        description.set("A small Maven2 library to download dependencies and receive information about them")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/license/mit")
            }
        }

        developers {
            developer {
                id.set("gradienttim")
                name.set("GradientTim")
                url.set("https://github.com/GradientTim")
            }
        }

        issueManagement {
            system.set("GitHub")
            url.set("https://github.com/GradientTim/m2/issues")
        }

        scm {
            url.set("https://github.com/GradientTim/m2")
            connection.set("scm:git:git://github.com/GradientTim/m2.git")
            developerConnection.set("scm:git:ssh://git@github.com/GradientTim/m2.git")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}