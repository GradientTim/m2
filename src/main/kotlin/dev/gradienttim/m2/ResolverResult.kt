package dev.gradienttim.m2

import dev.gradienttim.m2.dependency.MavenDependency
import dev.gradienttim.m2.dependency.source.DependencySource

class ResolverResult(
    val resolveTime: Long,
    val response: MutableMap<MavenDependency, DependencySource>,
) {

    operator fun get(dependency: MavenDependency): DependencySource =
        response[dependency] ?: error("Unknown response for dependency ${dependency.notation}")

    operator fun get(notation: String): DependencySource =
        response.entries.firstOrNull { (key) -> key.notation == notation }?.value
            ?: error("Unknown response for dependency $notation")

    operator fun get(index: Int): DependencySource {
        if (index < 0 || index > response.size) {
            error("Unknown response for index $index")
        }
        return response.toList()[index].second
    }

}