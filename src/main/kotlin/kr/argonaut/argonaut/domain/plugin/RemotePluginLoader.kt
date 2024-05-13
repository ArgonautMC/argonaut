package kr.argonaut.argonaut.domain.plugin

import kr.argonaut.argonaut.domain.plugin.model.PluginConfig
import kr.argonaut.argonaut.domain.manifest.plugin.PluginConfigManifest
import kr.argonaut.argonaut.domain.plugin.model.PluginJar
import kr.argonaut.argonaut.domain.manifest.plugin.PluginJarManifest

interface RemotePluginLoader {
    suspend fun load(manifest: PluginJarManifest): PluginJar
    suspend fun load(manifest: PluginConfigManifest): PluginConfig
}