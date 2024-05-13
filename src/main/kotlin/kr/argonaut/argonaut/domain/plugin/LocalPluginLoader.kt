package kr.argonaut.argonaut.domain.plugin

import kr.argonaut.argonaut.domain.manifest.plugin.PluginConfigManifest
import kr.argonaut.argonaut.domain.plugin.model.PluginConfig

interface LocalPluginLoader {
    suspend fun load(manifest: PluginConfigManifest): PluginConfig
}