package dev.gradienttim.m2.models

import kotlinx.serialization.Serializable

@Serializable
data class ArtifactModule(
    val formatVersion: String,
    val component: ModuleComponent,
    val createdBy: Map<String, ModuleCreatedBy>,
    val variants: List<ModuleVariant>,
)

@Serializable
data class ModuleComponent(
    val group: String,
    val module: String,
    val version: String,
    val attributes: Map<String, String>,
)

@Serializable
data class ModuleCreatedBy(
    val version: String,
    val buildId: String,
)

@Serializable
data class ModuleVariant(
    val name: String,
    val attributes: Map<String, String>,
    val dependencies: List<ModuleVariantDependency>,
    val files: List<ModuleVariantFile>,
    val capabilities: List<ModuleVariantCapability>,
)

@Serializable
data class ModuleVariantDependency(
    val group: String,
    val module: String,
    val version: ModuleVariantDependencyVersion,
)

@Serializable
data class ModuleVariantDependencyVersion(
    val requires: String,
)

@Serializable
data class ModuleVariantFile(
    val name: String,
    val url: String,
)

@Serializable
data class ModuleVariantCapability(
    val group: String,
    val name: String,
    val version: String,
)