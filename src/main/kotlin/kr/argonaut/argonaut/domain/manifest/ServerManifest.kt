package kr.argonaut.argonaut.domain.manifest

import kr.argonaut.argonaut.domain.manifest.plugin.PluginConfigManifest
import kr.argonaut.argonaut.domain.manifest.plugin.PluginJarManifest
import kr.argonaut.argonaut.domain.manifest.spigot.SpigotVersion

data class ServerManifest(
    var spigotVersion: SpigotVersion,
    val pluginJars: List<PluginJarManifest>,
    val pluginConfigs: List<PluginConfigManifest>,
)
