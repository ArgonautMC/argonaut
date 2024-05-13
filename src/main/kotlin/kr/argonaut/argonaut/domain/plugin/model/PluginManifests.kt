package kr.argonaut.argonaut.domain.plugin.model

import kr.argonaut.argonaut.domain.manifest.plugin.PluginConfigManifest
import kr.argonaut.argonaut.domain.manifest.plugin.PluginJarManifest

data class PluginManifests(
    val jarManifests: List<PluginJarManifest>,
    val configManifests: List<PluginConfigManifest>,
)