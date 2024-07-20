package dev.gradienttim.m2.dependency.source

enum class SourceType(
    val extension: String,
) {
    COMPILED(".jar"),
    SOURCE("-sources.jar"),
    JAVADOC("-javadoc.jar"),
    MODULE(".module"),
    POM(".pom"),
}