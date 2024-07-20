package dev.gradienttim.m2.kotlin

import dev.gradienttim.m2.MavenResolver
import dev.gradienttim.m2.helper.OkioHelper
import dev.gradienttim.m2.dependency.DependencyBuilder
import dev.gradienttim.m2.dependency.MavenDependency
import dev.gradienttim.m2.repository.MavenRepository
import dev.gradienttim.m2.repository.RepositoryBuilder
import okio.Source
import java.io.File
import java.net.Proxy

@KotlinDsl
fun repository(
    builder: RepositoryBuilder.() -> Unit,
) = MavenRepository.builder().apply(builder).build()

@KotlinDsl
fun dependency(
    builder: DependencyBuilder.() -> Unit,
) = MavenDependency.builder().apply(builder).build()

@KotlinDsl
fun resolveMaven(
    dependencies: List<MavenDependency>,
    proxy: Proxy? = null,
) = MavenResolver.resolve(dependencies, proxy)

@KotlinDsl
fun resolveMaven(
    vararg dependencies: MavenDependency,
    proxy: Proxy? = null,
) = MavenResolver.resolve(dependencies.toList(), proxy)

fun Source.toFile(file: File) =
    OkioHelper.sourceToFile(this, file)