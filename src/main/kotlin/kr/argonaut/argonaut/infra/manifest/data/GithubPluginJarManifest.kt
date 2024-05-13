package kr.argonaut.argonaut.infra.manifest.data

import kr.argonaut.argonaut.domain.manifest.plugin.PluginJarManifest
import kr.argonaut.argonaut.domain.manifest.plugin.PluginVersion

data class GithubPluginJarManifest(
    val groupId: String,
    val artifactId: String,
    val version: String,
) {
    fun toManifest(): PluginJarManifest {
        return PluginJarManifest(
            groupId = groupId,
            artifactId = artifactId,
            version = PluginVersion.fromString(version)
        )
    }
}