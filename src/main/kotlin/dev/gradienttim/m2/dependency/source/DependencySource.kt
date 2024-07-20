package dev.gradienttim.m2.dependency.source

import dev.gradienttim.m2.models.ArtifactModule
import okio.Source

data class DependencySource(
    val compiled: SourceSection<Source>,
    val source: SourceSection<Source>,
    val javadoc: SourceSection<Source>,
    val module: SourceSection<ArtifactModule>,
    val pom: SourceSection<Source>,
)