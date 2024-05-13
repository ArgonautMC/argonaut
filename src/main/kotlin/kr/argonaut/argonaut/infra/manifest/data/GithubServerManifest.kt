package kr.argonaut.argonaut.infra.manifest.data

import kr.argonaut.argonaut.domain.manifest.ServerManifest
import kr.argonaut.argonaut.domain.manifest.plugin.PluginConfigManifest
import kr.argonaut.argonaut.domain.manifest.spigot.SpigotVersion

data class GithubServerManifest(
    var spigotVersion: String,
    val pluginJars: List<GithubPluginJarManifest>,
    val pluginConfigs: List<PluginConfigManifest>,
) {
    fun toManifest(): ServerManifest {
        val spigotVersion = SpigotVersion.fromString(spigotVersion)
        val pluginJars = pluginJars.map(GithubPluginJarManifest::toManifest)

        return ServerManifest(
            spigotVersion = spigotVersion,
            pluginJars = pluginJars,
            pluginConfigs = pluginConfigs,
        )
    }
}
