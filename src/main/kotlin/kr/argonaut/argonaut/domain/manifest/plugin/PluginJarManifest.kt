package kr.argonaut.argonaut.domain.manifest.plugin

data class PluginJarManifest(
    val groupId: String,
    val artifactId: String,
    val version: PluginVersion,
)