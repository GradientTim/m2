# Kotlin Usage

### DSL (recommended)
```kotlin
val repository = repository {
    url("https://repo1.maven.org/")
    id("maven2") // not required, 'maven2' is set by default
}

val dependency = dependency {
    repository(repository)
    groupId("com.google.guava")
    artifactId("guava")
    version("33.2.1-jre")
    // notation("com.google.guava:guava:33.2.1-jre") // also works
}

val result = resolveMaven(dependency)
```

### Class
```kotlin
val repository = MavenRepository.builder()
    .url("https://repo1.maven.org/")
    .id("maven2") // not required, 'maven2' is set by default
    .build()

val dependency = MavenDependency.builder()
    .repository(repository)
    .groupId("com.google.guava")
    .artifactId("guava")
    .version("33.2.1-jre")
    // .notation("com.google.guava:guava:33.2.1-jre") // also works
    .build()

val result = MavenResolver.resolve(dependency)
```