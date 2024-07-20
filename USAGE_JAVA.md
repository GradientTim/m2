# Java Usage

```java
MavenRepository repository = MavenRepository.builder()
        .url("https://repo1.maven.org/")
        .id("maven2") // not required, 'maven2' is set by default
        .build();

MavenDependency dependency = MavenDependency.builder()
        .repository(repository)
        .groupId("com.google.guava")
        .artifactId("guava")
        .version("33.2.1-jre")
        // .notation("com.google.guava:guava:33.2.1-jre") // also works
        .build();

List<MavenDependency> dependencies = List.of(dependency);
ResolverResult result = MavenResolver.resolve(dependencies);
// do whatever you want with the result
```